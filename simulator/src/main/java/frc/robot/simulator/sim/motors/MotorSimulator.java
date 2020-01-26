package frc.robot.simulator.sim.motors;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import frc.robot.simulator.network.proto.RobotProto;
import frc.robot.simulator.sim.config.SimulatorConfig;
import frc.robot.simulator.sim.events.EventManager;
import frc.robot.simulator.sim.utils.MathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.concurrent.*;

@Singleton
public class MotorSimulator {
    private static final Logger log = LoggerFactory.getLogger(MotorSimulator.class);
    private MotorStore motorStore;

    @Inject
    public MotorSimulator(MotorStore motorStore) {
        this.motorStore = motorStore;
    }

    /**
     * Simulate each motor based on its motor config
     * @param deltaTime
     * @return
     */
    public RobotProto.MotorOutputs simulateMotors(long deltaTime) {
        RobotProto.MotorOutputs.Builder reply = RobotProto.MotorOutputs.newBuilder();
        for (SimMotor motor : motorStore.getSimMotors()) {
            reply.addMotorOutput(simulateMotor(motor, motorStore.getSimMotors(), deltaTime));
        }

        return reply.build();
    }


    /**
     * Update this motor during the loop. This computes the output percentage based on the PID controller
     * and also updates the velocity and position of the motor
     *
     * @param deltaTime The time (in ms) since the last update
     */
    RobotProto.MotorOutputs.MotorOutput simulateMotor(SimMotor motor, Collection<SimMotor> motors, long deltaTime) {

        RobotProto.MotorOutputs.MotorOutput.Builder outputBuilder = RobotProto.MotorOutputs.MotorOutput.newBuilder();

        RobotProto.MotorConfig.ControlMode controlMode = motor.getConfig().getControlMode();
        double output = 0;
        if (motor.getConfig().getFollowingId() != 0) {
            for (SimMotor otherMotor : motors) {
                if (otherMotor.getId() == motor.getConfig().getFollowingId()) {
                    output = otherMotor.getOutput();
                    break;
                }
            }
        } else {
            switch (controlMode) {

                case PercentOutput:
                    output = motor.getConfig().getTargetOutput();
                    break;
                case Position:
                    // compute position based on our target
                    output = runPID(motor, motor.getConfig().getTargetPosition(), motor.getSensorPosition());
                    break;
                case Velocity:
                    // compute position based on our target
                    output = runPID(motor, motor.getConfig().getTargetVelocity(), motor.getVelocity());
                    break;
                case Current:
                    break;
                case Follower:
                    break;
                case MotionProfile:
                    break;
                case MotionPosition:
                    output = runPID(motor, motor.getConfig().getTargetPosition(), motor.getSensorPosition());
                    break;
                case MotionProfileArc:
                    break;
                case Disabled:
                    output = 0;
                    break;
            }
        }

        // clamp the output between -1 and 1
        output = MathUtils.clamp(output);

        // run the physics simulation of the motor with the computed output this motor
        motor.setOutput(output);
        motor.step(output * 12.0, 0, deltaTime);

        // update the selectedSensorPosition
        motor.setConfig(motor.getConfig().toBuilder().setSelectedSensorPosition(motor.getSensorPosition()).build());

        return outputBuilder
                .setId(motor.getId())
                .setHandle(motor.getHandle())
                .setInputVoltage(motor.getVoltage())
                .setOutput(motor.getOutput())
                .setOutputCurrent(motor.getCurrent())
                .setPosition(motor.getPosition())
                .setSensorPosition(motor.getSensorPosition())
                .setVelocity(motor.getVelocity())
                .setLastError(motor.getLastError())
                .setIntegralState(motor.getIntegralState())
                .build();
    }

    /**
     * Run a motor through a PID loop
     *
     * logic taken from here
     * http://www.revrobotics.com/sparkmax-users-manual/#section-3-4
     *
     * @param motor The motor with pid constants and error calcs
     * @param setPoint The point
     * @param pv The process variable (or current position, velocity, etc)
     * @return
     */
    double runPID(SimMotor motor, double setPoint, double pv) {
        RobotProto.MotorConfig.FPID fpid = motor.getConfig().getFpid(motor.getConfig().getCurrentPidProfile());

        // calculate p
        double error = setPoint - pv;
        double pComp = error * fpid.getP();

        // calculate the (iState or integralState)
        if(Math.abs(error) <= fpid.getIZone() || fpid.getIZone() == 0.0f) {
            motor.setIntegralState(motor.getIntegralState() + (error * fpid.getI()));
        } else {
            motor.setIntegralState(0);
        }

        // calculate the d component
        double dComp = (error - motor.getLastError());
        motor.setLastError(error);
        dComp *= fpid.getD();

        // calculate the f component
        double fComp = setPoint * fpid.getF();

        // for a PID controller, the output is the sum of the components
        double output = pComp + motor.getIntegralState() + dComp + fComp;

        return output;
    }

}
