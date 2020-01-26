package frc.robot.simulator.sim.motors;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import frc.robot.simulator.network.proto.RobotProto;
import frc.robot.simulator.sim.config.SimulatorConfig;
import frc.robot.simulator.sim.events.EventManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Singleton
public class MotorStore {
    private static final Logger log = LoggerFactory.getLogger(MotorStore.class);

    /**
     * The SimulatorConfig is injected
     */
    private final SimulatorConfig simulatorConfig;

    private final Map<Long, SimMotor> simMotorsByHandle = new ConcurrentHashMap<>();
    private RobotProto.MotorOutputs simMotorOutputs = RobotProto.MotorOutputs.newBuilder().build();
    private static Map<Long, RobotProto.MotorOutputs.MotorOutput> motorOutputsByHandle = new HashMap<>();

    private Object simulatorLock = new Object();

    @Inject
    public MotorStore(SimulatorConfig simulatorConfig) {
        this.simulatorConfig = simulatorConfig;

        EventManager.subscribeToMotorOutputsEvents(this::onMotorOutputsUpdated);
    }

    /**
     * Talon motors do some funkiness to the id the user passes in, so if the user says
     * "I want motor with id 1", talons actually tell us the id is 33816577
     * i.e. 1 | 0x02040000
     * <p>
     * We can reverse this to get the user specified id with  talonId & (~0x02040000)
     *
     * @param talonId
     * @return
     */
    public static int idFromTalonId(int talonId) {
        return talonId & (~0x02040000);
    }

    /**
     * Handle motors from different vendors. This will support a motor id of 1 for talons and 1 for
     * sparks with different handles. These are memory pointers in real life, but we are just simulating
     * them anyway
     *
     * @param id     The user id, i.e. 1, 2, 3, 4
     * @param vendor The vendor of the motor
     * @return a vendor unique handle for a motor
     */
    public static long handleFromId(int id, Vendor vendor) {
        switch (vendor) {
            case CTRE:
                return 100000 + id;
            case Rev:
                return 200000 + id;
            default:
                return 300000 + id;
        }
    }

    /**
     * Create a new SimDCMotor based on motor specs
     *
     * @param model        The model of the motor, i.e. BAG, MiniCIM, Neo
     * @param stallTorque  The torque at stall, in Nm
     * @param stallCurrent The max current at stall, in Amps
     * @param voltage      The in voltage (i.e. 12) in volts
     * @param maxRPM       The maximum RPMs when freely spinning
     * @param mass         The mass, in kg
     * @param diameter     The diameter, in meters
     * @return A new SimDCMotor with the kv, kt, resistance and inertia values calculated
     */
    public static SimDCMotor makeMotor(String model, double stallTorque, double stallCurrent, double voltage, int maxRPM, double mass, double diameter) {
        double kt = stallTorque / stallCurrent; // in nm/A
        double kv = voltage / (maxRPM * Math.PI / 60); // in volts / (rads/second)
        double resistance = voltage / stallCurrent; // R = V / I
        double inertia = .5 * mass * (.5 * diameter * .5 * diameter); // mass of a cylinder is I = .5 * m * r^2
        // viscousFrictionConstant = 𝑏 = 𝑇 / 𝜔, according to the internet
        // where 𝜔 is the RPM of the motor
        double viscousFriction = (stallTorque - kt * stallCurrent) / (maxRPM * 2 * Math.PI * 60);
        return new SimDCMotor(model, kt, kv, resistance, inertia);
    }

    // Convenience methods for constructing common motors.
    public static SimDCMotor make775pro() {
        // I'm not sure these numbers all work:
        // https://motors.vex.com/vexpro-motors/775pro
        return makeMotor("775pro",
                .71,
                134,
                12,
                18700,
                0.362874,
                .0423
        );
    }

    public static SimDCMotor makeNeo() {
        return makeMotor("NEO",
                2.6,
                105,
                12,
                5676,
                .425,
        .06);
    }

    public static SimDCMotor makeFromConfig(SimulatorConfig.Motor config) {
        SimDCMotor motor;
        if (config.stallTorque > 0) {
            motor = makeMotor(
                    config.model,
                    config.stallTorque,
                    config.stallCurrent,
                    config.voltage,
                    config.maxRPM,
                    config.mass,
                    config.diameter
            );
        } else {
            motor = new SimDCMotor(
                    config.model,
                    config.kt,
                    config.kv,
                    config.resistance,
                    config.inertia
            );
        }
        return motor;
    }

    /**
     * Make a transmission.
     *
     * @param motor      The motor type attached to the transmission.
     * @param numMotors  The number of motors in this transmission.
     * @param gearRatio  The reduction of the transmission, i.e. 242.0/27.0 for Junior
     * @param efficiency The efficiency of the transmission.
     * @return A DCMotor representing the combined transmission.
     */
    public static SimDCMotor makeTransmission(SimDCMotor motor, int numMotors, double gearRatio, double efficiency) {
        return new SimDCMotor(
                motor.getConfig().getModel(),
                numMotors * gearRatio * efficiency * motor.kt,
                motor.kv * gearRatio,
                motor.resistance / numMotors,
                motor.inertia * numMotors * gearRatio * gearRatio);
    }

    /**
     * From a CAN id and vendor, create a new SimMotor
     *
     * @param baseArbId
     * @param vendor
     * @return
     */
    public SimMotor createOrUpdateMotor(int baseArbId, Vendor vendor) {
        int id = baseArbId;
        if (vendor == Vendor.CTRE) {
            id = idFromTalonId(baseArbId);
        }
        long handle = handleFromId(id, vendor);

        // the baseArbId is actually the handle, at least for talons
        SimMotor simMotor = simMotorsByHandle.get(handle);
        if (simMotor == null) {
            log.info("Creating new SimMotor, id: " + id + ", handle: " + handle);
            // check for simulator config for this motor
            SimulatorConfig.Motor motorConfig = simulatorConfig.getConfigForMotor(id);
            if (motorConfig == null) {
                motorConfig = simulatorConfig.addNewDefaultMotor(id, vendor);
            }

            simMotor = makeTransmission(
                    makeFromConfig(motorConfig),
                    motorConfig.transmission.motors.size(),
                    motorConfig.transmission.gearRatio,
                    motorConfig.transmission.efficiency
            );

            simMotor.init(handle, id, motorConfig.name, motorConfig.model, motorConfig.encoderCountsPerRevolution);
            simMotor.setVendor(vendor);
            simMotor.setLeftDriveMotor(motorConfig.leftDrive);
            simMotor.setRightDriveMotor(motorConfig.rightDrive);

            simMotorsByHandle.put(simMotor.getHandle(), simMotor);

            // add a new MotorOutput for this motor
            simMotorOutputs = simMotorOutputs
                    .toBuilder()
                    .addMotorOutput(RobotProto.MotorOutputs.MotorOutput
                            .newBuilder()
                            .setId(simMotor.getId())
                            .setHandle(simMotor.getHandle())
                            .build())
                    .build();

            // build a new default output until it's updated by the server
            motorOutputsByHandle.put(simMotor.getHandle(), RobotProto.MotorOutputs.MotorOutput.newBuilder().build());
        }
        return simMotor;
    }

    public void updateMotorConfig(RobotProto.MotorConfig config) {
        SimMotor simMotor = simMotorsByHandle.get(config.getHandle());
        synchronized (simulatorLock) {
            if (simMotor == null) {
                throw new RuntimeException("Trying to update a motor that hasn't been created yet");
            }

            if (config.getSelectedSensorPosition() != simMotor.getConfig().getSelectedSensorPosition()) {
                simMotor.setSensorPosition(config.getSelectedSensorPosition());
            }

            // update this motor's config
            simMotor.setConfig(config);

            // publish this output update
            EventManager.publish(simMotorOutputs);
        }
    }

    public Collection<SimMotor> getSimMotors() {
        return simMotorsByHandle.values();
    }

    public List<SimMotor> getSimMotorsSorted() {
        return simMotorsByHandle.values().stream().sorted(Comparator.comparingInt(SimMotor::getId)).collect(Collectors.toList());
    }

    public SimMotor get(long handle) {
        return simMotorsByHandle.get(handle);
    }

    public RobotProto.MotorOutputs.MotorOutput getOutput(long handle) {
        return motorOutputsByHandle.get(handle);
    }

    private void onMotorOutputsUpdated(RobotProto.MotorOutputs motorOutputs) {
        for (RobotProto.MotorOutputs.MotorOutput output : motorOutputs.getMotorOutputList()) {
            motorOutputsByHandle.put(output.getHandle(), output);
            // update the sensor position in config to whatever the output has
            SimMotor simMotor = simMotorsByHandle.get(output.getHandle());
            simMotor.setConfig(simMotor.getConfig().toBuilder().setSelectedSensorPosition(output.getSensorPosition()).build());
            simMotor.setSensorPosition(output.getSensorPosition());
        }
    }
}
