package frc.robot.simulator.sim.field.howard;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import frc.robot.simulator.sim.RobotPosition;
import frc.robot.simulator.sim.config.SimulatorConfig;
import frc.robot.simulator.sim.field.FieldSim;
import frc.robot.simulator.sim.motors.MotorStore;
import frc.robot.simulator.sim.motors.SimDCMotor;
import frc.robot.simulator.sim.motors.SimMotor;

/**
 * Physics simulation using Howard's motor forces math
 * Note: This is an old version of his math and there are bugs (probably both in the math and the implementation)
 */
@Singleton
public class HowardSim extends FieldSim {

    @Inject
    public HowardSim(MotorStore motorStore, SimulatorConfig simulatorConfig) {
        super(motorStore, simulatorConfig);
    }

    @Override
    protected RobotPosition supplyRobotPosition() {
        return new RobotPosition(RobotPosition.Type.HowardPhysics);
    }

    @Override
    public void create() {
        super.create();
        robotPosition.heading = Math.PI / 2;
    }

    /**
     * Simulate robot position using HowardMagic™
     *
     * @param deltaTime The deltaTime for this step, in seconds
     */
    @Override
    public void step(double deltaTime) {

        double leftMotorForce = 0;
        double rightMotorForce = 0;
        double linearForce = 0;
        for (SimMotor motor : motorStore.getSimMotorsSorted()) {
            if ((motor.isLeftDriveMotor() || motor.isRightDriveMotor()) && motor instanceof SimDCMotor) {

                SimDCMotor dcMotor = (SimDCMotor) motor;
                double wheelRadius = simulatorConfig.driveBase.wheelRadius;
                double kt = dcMotor.kt;
                double kv = dcMotor.kv;
                double resistance = dcMotor.resistance;
                double voltage = dcMotor.voltage;
                double velocity = dcMotor.velocity;
                double viscousFrictionConstant = simulatorConfig.driveBase.viscousFriction;

                // force is
                double force = kt / (resistance * wheelRadius) * voltage - ((kt * kv) / (resistance * wheelRadius) + viscousFrictionConstant / wheelRadius) * velocity;
                motor.force = force;

                linearForce += force;
                if (motor.isLeftDriveMotor()) {
                    leftMotorForce += force;
                } else {
                    rightMotorForce += force;
                }
            }
        }

        double linearAcceleration = (1 / simulatorConfig.driveBase.mass) * linearForce;
        double angularAcceleration = (simulatorConfig.driveBase.radius / simulatorConfig.driveBase.robotInertia) * (leftMotorForce - rightMotorForce);

        double velocity = robotPosition.velocity + linearAcceleration * deltaTime;
        double angularVelocity = robotPosition.angularVelocity + angularAcceleration * deltaTime;
        double heading = robotPosition.heading + robotPosition.angularVelocity * deltaTime + .5 * angularAcceleration * deltaTime * deltaTime;

        if (Math.abs(linearAcceleration) > .01) {
            robotPosition.velocity = velocity;
        } else {
            robotPosition.velocity = 0;
        }
        if (Math.abs(angularAcceleration) > .01) {
            robotPosition.angularVelocity = angularVelocity;
            robotPosition.heading = heading;
        } else {
            robotPosition.angularVelocity = 0;
        }
        robotPosition.x += robotPosition.velocity * deltaTime * Math.cos(heading);
        robotPosition.y += robotPosition.velocity * deltaTime * Math.sin(heading);
    }

}
