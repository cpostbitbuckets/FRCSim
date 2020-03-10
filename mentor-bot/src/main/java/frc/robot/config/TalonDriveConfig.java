package frc.robot.config;

import frc.robot.config.settings.FPID;
import frc.robot.config.settings.MotorSettings;
import frc.robot.config.settings.SensorType;

public class TalonDriveConfig extends SubsystemConfig {

    public final int maxVelocity = 6000;

    FPID velocityFPID = new FPID(
            Constants.ctreVoltageReslution / 21740f, // we can go 20k ticks per 100ms at full (1023) voltage
            0.1 * 1023 / 1000 / 4, //.01 * 1023 / 5000, // error of 5000 ticks
            0, // .001 our simulated motor is so perfect we don't need i or d
            0.1 * 1023 / 1000 / 4 * 10 // 10.0 * (0.1 * 1023 / 5000) // set d = 10 * p
    );

    public MotorSettings[] leftMotors = new MotorSettings[]{
            MotorSettings.build(Constants.TALON_DRIVE_LEFT_IDS[0])
                    .sensor(SensorType.Integrated)
                    .encoderTicks(2048)
                    .velocityFPID(velocityFPID)
                    .inverted(false),
            MotorSettings.build(Constants.TALON_DRIVE_LEFT_IDS[1])
                    .sensor(SensorType.Integrated)
                    .encoderTicks(2048)
                    .inverted(false)
                    .follower(true)
    };

    public MotorSettings[] rightMotors = new MotorSettings[]{
            MotorSettings.build(Constants.TALON_DRIVE_RIGHT_IDS[0])
                    .sensor(SensorType.Integrated)
                    .encoderTicks(2048)
                    .velocityFPID(velocityFPID)
                    .inverted(true)
            ,
            MotorSettings.build(Constants.TALON_DRIVE_RIGHT_IDS[1])
                    .sensor(SensorType.Integrated)
                    .encoderTicks(2048)
                    .follower(true)
                    .inverted(true)

    };

}
