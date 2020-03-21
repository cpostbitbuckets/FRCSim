package frc.robot.config;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.config.settings.FPID;
import frc.robot.config.settings.MotorSettings;

public class SparkDriveConfig extends SubsystemConfig {

    public SparkDriveConfig() {
        // disable this subsystem
        enabled = false;
    }

    // These motors go 316 RPMs at full throttle, so set velocity to something lower
    public final int maxVelocity = 200;

    FPID velocityFPID = new FPID(
            1. / 316., // we can go 316 RPMs at full throttle
            0, //.01 / 5000, // error of 5000 ticks
            0, // .001 our simulated motor is so perfect we don't need i or d
            0 // 10.0 * (0.1 / 5000) // set d = 10 * p
    );

    public MotorSettings[] leftMotors = new MotorSettings[]{
            MotorSettings.build(Constants.SPARK_DRIVE_LEFT_IDS[0])
                    .velocityFPID(velocityFPID),
            MotorSettings.build(Constants.SPARK_DRIVE_LEFT_IDS[1])
                    .follower(true)
    };

    public MotorSettings[] rightMotors = new MotorSettings[]{
            MotorSettings.build(Constants.SPARK_DRIVE_RIGHT_IDS[0])
                    .velocityFPID(velocityFPID)
                    .inverted(true)
            ,
            MotorSettings.build(Constants.SPARK_DRIVE_RIGHT_IDS[0])
                    .inverted(true)
                    .follower(true)

    };
}
