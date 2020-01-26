package frc.robot.config;

import frc.robot.config.settings.FPID;
import frc.robot.config.settings.MotorSettings;

public class SparkDriveConfig implements SimSupportingConfig {

    // These motors go 316 RPMs at full throttle, so set velocity to something lower
    public final int maxVelocity = 200;

    public MotorSettings[] leftMotors = new MotorSettings[] {
            MotorSettings.build(9)
                    .inverted(false)
                    .velocityFPID(
                    0,
                    0,
                    0,
                    0
                    ),
            MotorSettings.build(10),
            MotorSettings.build(11)
    };

    public MotorSettings[] rightMotors = new MotorSettings[] {
            MotorSettings.build(12)
                    .inverted(false)
                    .velocityFPID(
                    0,
                    0,
                    0,
                    0
            ),
            MotorSettings.build(13),
            MotorSettings.build(14)
    };

    @Override
    public void overrideForSim() {

        FPID velocityFPID = new FPID(
                1. / 316., // we can go 316 RPMs at full throttle
                0, //.01 / 5000, // error of 5000 ticks
                0, // .001 our simulated motor is so perfect we don't need i or d
                0 // 10.0 * (0.1 / 5000) // set d = 10 * p
        );

        // simulator
        leftMotors = new MotorSettings[] {
                MotorSettings.build(7)
                        .velocityFPID(velocityFPID),
                MotorSettings.build(8)
                        .follower(true)
        };

        rightMotors = new MotorSettings[] {
                MotorSettings.build(9)
                        .velocityFPID(velocityFPID)
                        .inverted(true)
                ,
                MotorSettings.build(10)
                        .inverted(true)
                        .follower(true)

        };

    }
}
