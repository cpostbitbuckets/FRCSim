package frc.robot.simulator.sim.field.wheeldisplacement;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
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

    private DifferentialDriveOdometry odometry;

    @Inject
    public WheelDisplacementSim(MotorStore motorStore, SimulatorConfig simulatorConfig) {
        super(motorStore, simulatorConfig);
    }

    @Override
    public void create() {
        super.create();
    }

    @Override
    public void resetRobot() {
        super.resetRobot();
        Rotation2d rotation = new Rotation2d(robotPosition.heading);
        odometry = new DifferentialDriveOdometry(
                rotation,
                new Pose2d(robotPosition.x, robotPosition.y, rotation)
        );
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
        rightRadians = -rightRadians;

        double metersPerRadian = wheelCircumference / (Math.PI * 2);
        double newHeading = ((leftRadians - rightRadians) * metersPerRadian / simulatorConfig.driveBase.radius);

        odometry.update(new Rotation2d(newHeading + startHeading), leftRadians * metersPerRadian, rightRadians * metersPerRadian);

        robotPosition.heading = odometry.getPoseMeters().getRotation().getRadians();
        robotPosition.x = odometry.getPoseMeters().getTranslation().getX();
        robotPosition.y = odometry.getPoseMeters().getTranslation().getY();

        SimNavX simNavX = SimSPI.getNavX(SPI.Port.kMXP.value);
        if (simNavX != null) {
            float degrees = (float)((robotPosition.heading - simulatorConfig.startPosition.heading) * 360 / (Math.PI * 2));

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

}
