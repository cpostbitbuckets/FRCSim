package frc.robot.simulator.sim.config;

import frc.robot.simulator.sim.RobotPosition;
import frc.robot.simulator.sim.motors.Vendor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This configuration configures how the simulator itself runs
 * This is loaded from the robot project and used to configure the motors for a robot
 * Each robot's motors will have different physics and require different config
 */
public class SimulatorConfig {

    public static class DriveBase {
        // Default to HowardPhysics™ because it's better
        // TODO: Delete the WheelDisplacementSim?
        public RobotPosition.Type positionalSimType = RobotPosition.Type.Physics;
        public boolean showAllSims = false;

        // radius of the robot and wheels in meters
        public double radius = 0.30353879642132;
        public double wheelRadius = 0.0762;
        public double gearRatio = (10 + 8.0 / 9);

        public double[][] ssAc = new double[][] {
            new double[] {-7.087195478354283, 0.413285738104402},
            new double[] {0.339280393075371,  -6.832080740045777}
        };

        public double[][] ssBc = new double[][] {
            new double[] {2.702895517197959, -0.241632861263366},
            new double[] {-0.126961060623545, 2.551095849721741}
        };
    }

    public static class Transmission {
        public List<Motor> motors = new ArrayList<>();
        public double gearRatio = 1;
        public double efficiency = 1;

        public Transmission() {
        }

        public Transmission(Motor motor) {
            motors.add(motor);
            motor.transmission = this;
        }
    }

    public static Motor defaultCTRE(int id) {
        Motor motor = new Motor(id);
        motor.name = "";
        motor.model = "BAG";

        // from https://www.vexrobotics.com/217-3351.html#Size
        motor.stallTorque = .4;
        motor.stallCurrent = 53;
        motor.voltage = 12;
        motor.maxRPM = 13180;
        motor.mass = .32;
        motor.diameter = .0404;

        return motor;
    }

    public static Motor defaultRev(int id) {
        Motor motor = new Motor(id);
        motor.name = "";
        motor.model = "NEO";

        // from http://www.revrobotics.com/rev-21-1650/
        motor.stallTorque = 2.6;
        motor.stallCurrent = 105;
        motor.voltage = 12;
        motor.maxRPM = 5676;
        motor.mass = .425;
        motor.diameter = .06;

        return motor;
    }

    public static class Motor {
        public int id;
        public String name;
        public String model;
        // is this a left drive or right drive motor?
        public boolean leftDrive;
        public boolean rightDrive;

        // motor config can either be physical characteristics, or
        // specific kt, kv, resistance, and inertia values
        public double stallTorque;
        public double stallCurrent;
        public double voltage;
        public int maxRPM; 
        public double mass;
        public double diameter;
        
        public double kt;
        public double kv;
        public double resistance;
        public double inertia;

        public int encoderCountsPerRevolution = 1024;

        transient public Transmission transmission;

        public Motor() {}
        public Motor(int id) {
            this.id = id;
        }
    }

    public static class Solenoid {
        public int module = 0;
        public String name = "Solenoid";
    }

    public boolean hideFollowers = true;
    public boolean remapXboxController = true;
    public DriveBase driveBase = new DriveBase();
    public List<Transmission> transmissions = new ArrayList<>();
    public List<Solenoid> solenoids = new ArrayList<>();

    public static class StartPosition {
        public double x = 1.54; // meters from center
        public double y = 5.43; // meters from center
        public double heading = 0.0; // radians, 0 = up
    }

    public StartPosition startPosition = new StartPosition();

    private final Map<Integer, Motor> motorsById = new HashMap<>();
    private final Map<Integer, Solenoid> solenoidsByModule = new HashMap<>();

    /**
     * Add a new default motor (if not found in config) with a default name
     * @param id
     * @param vendor
     * @return
     */
    public Motor addNewDefaultMotor(int id, Vendor vendor) {
        Motor motor;
        if (vendor == Vendor.Rev) {
            motor = defaultRev(id);
        } else {
            motor = defaultCTRE(id);
        }
        motor.name = motor.model + " (" + id + ")";

        transmissions.add(new Transmission(motor));
        motorsById.put(id, motor);
        return motor;
    }

    public Solenoid addNewDefaultSolenoid(int module) {
        Solenoid solenoid = new Solenoid();
        solenoid.module = module;
        solenoid.name = "Solenoid (" + module + ")";
        return solenoid;
    }

    /**
     * Initialize this config after being loaded from yaml
     */
    public void init() {
        motorsById.clear();
        transmissions.stream().forEach(t -> t.motors.stream().forEach(m -> {
            motorsById.put(m.id, m);
            m.transmission = t;
        }));

        solenoidsByModule.clear();
        solenoids.stream().forEach(s -> solenoidsByModule.put(s.module, s));
    }

    public Motor getConfigForMotor(int id) {
        return motorsById.get(id);
    }

    public Solenoid getConfigForSolenoid(int module) {
        return solenoidsByModule.get(module);
    }


}
