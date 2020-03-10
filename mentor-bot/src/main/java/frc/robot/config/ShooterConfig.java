package frc.robot.config;

import frc.robot.config.settings.FPID;
import frc.robot.config.settings.MotorSettings;
import frc.robot.config.settings.SensorType;


public class ShooterConfig extends SubsystemConfig {

    public int spinUpSpeed = 6700;

    FPID velocityPID = new FPID(
            0, // F
            1023., // P bang bang with p only
            0, // I
            0 // D
    );

    public MotorSettings[] shooterMotors = new MotorSettings[] {
            MotorSettings.build(Constants.SHOOTER_IDS[0])
                    .sensor(SensorType.Integrated)
                    .encoderTicks(2048)
                    .velocityFPID(velocityPID)
                    .inverted(true),
            MotorSettings.build(Constants.SHOOTER_IDS[1])
                    .sensor(SensorType.Integrated)
                    .encoderTicks(2048)
                    .follower(true)
                    .inverted(true)
    };

    public MotorSettings feederMotor = MotorSettings.build(Constants.FEEDER_ID);
}
