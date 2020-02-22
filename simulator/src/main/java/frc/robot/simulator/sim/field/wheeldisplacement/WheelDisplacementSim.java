package frc.robot.simulator.sim.field.wheeldisplacement;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.wpi.first.wpilibj.SPI;
import frc.robot.simulator.sim.RobotPosition;
import frc.robot.simulator.sim.SimSPI;
import frc.robot.simulator.sim.config.SimulatorConfig;
import frc.robot.simulator.sim.field.FieldSim;
import frc.robot.simulator.sim.ic2.SimNavX;
import frc.robot.simulator.sim.motors.MotorStore;
import frc.robot.simulator.sim.motors.SimMotor;

/**
 * This is a positional simulator based on wheel displacement
 */
@Singleton
public class WheelDisplacementSim extends FieldSim {

    private double lastLinearRadians = 0;

    @Inject
    public WheelDisplacementSim(MotorStore motorStore, SimulatorConfig simulatorConfig) {
        super(motorStore, simulatorConfig);
    }

    /**
     * Move the robot based on rotations
     *
     * @param deltaTime The deltaTime for this step, in seconds
     */
    @Override
    public void step(double deltaTime) {
        // wheel radius, in meters
        double wheelCircumference = 2 * simulatorConfig.driveBase.wheelRadius * Math.PI;

        double leftRadians = 0;
        double rightRadians = 0;
        for (SimMotor simMotor : motorStore.getSimMotorsSorted()) {
            if (simMotor.isLeftDriveMotor()) {
                leftRadians = simMotor.position / simulatorConfig.driveBase.gearRatio;
            } else if (simMotor.isRightDriveMotor()) {
                rightRadians = simMotor.position / simulatorConfig.driveBase.gearRatio;
            }
        }

        // invert the left side because forward motor movements mean backwards wheel movements
        leftRadians = -leftRadians;

        double currentLinearRadians = (leftRadians + rightRadians) / 2;

        double deltaRadians = currentLinearRadians - lastLinearRadians;
        double metersPerRadian = wheelCircumference / (Math.PI * 2);
        double deltaLinearPosition = deltaRadians * metersPerRadian;
        double newHeading = ((leftRadians - rightRadians) * metersPerRadian / simulatorConfig.driveBase.radius);

        // for next loop
        lastLinearRadians = currentLinearRadians;

        robotPosition.heading = newHeading;

        robotPosition.velocity = deltaLinearPosition / deltaTime;
        robotPosition.x += deltaLinearPosition * Math.sin(robotPosition.heading);
        robotPosition.y += deltaLinearPosition * Math.cos(robotPosition.heading);

        SimNavX simNavX = SimSPI.getNavX(SPI.Port.kMXP.value);
        if (simNavX != null) {
            float degrees = (float)(robotPosition.heading * 360 / (Math.PI * 2));

            // degrees are between 0 and 360
            if (degrees < 0) {
                degrees = 360 - (Math.abs(degrees) % 360);
            } else {
                degrees = degrees % 360;
            }
            simNavX.heading = degrees;
        }

    }

    @Override
    protected RobotPosition supplyRobotPosition() {
        return new RobotPosition(RobotPosition.Type.WheelDisplacement);
    }

    @Override
    public void resetRobot() {
        super.resetRobot();
        lastLinearRadians = 0;
    }
}
