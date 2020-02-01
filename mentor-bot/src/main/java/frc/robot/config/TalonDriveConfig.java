package frc.robot.config;

import frc.robot.config.settings.FPID;
import frc.robot.config.settings.MotorSettings;
import frc.robot.config.settings.SensorType;

public class TalonDriveConfig implements SimSupportingConfig {

    public final int maxVelocity = 21000;

    public MotorSettings[] leftMotors = new MotorSettings[] {
            MotorSettings.build(1)
                    .inverted(false)
                    .sensor(SensorType.Quadrature)
                    .encoderTicks(2048)
                    .velocityFPID(
                    0.114944,
                    0.683333 / 2 / 2,
                    0.0001,
                    10 * 0.683333 / 2 / 1.5
                    ),
            MotorSettings.build(2)
                    .sensor(SensorType.Quadrature)
                    .encoderTicks(2048)
            ,
            MotorSettings.build(3)
                    .sensor(SensorType.Quadrature)
                    .encoderTicks(2048)

    };

    public MotorSettings[] rightMotors = new MotorSettings[] {
            MotorSettings.build(4)
                    .inverted(false)
                    .sensor(SensorType.Quadrature)
                    .encoderTicks(2048)
                    .velocityFPID(
                    0.114944,
                    0.683333 / 2 / 2, ///1.5,
                    0.0001,
                    10 * 0.683333 / 2 / 1.5
            ),
            MotorSettings.build(5)
                    .sensor(SensorType.Quadrature)
                    .encoderTicks(2048)
            ,
            MotorSettings.build(6)
                    .sensor(SensorType.Quadrature)
                    .encoderTicks(2048)

    };

    @Override
    public void overrideForSim() {

        FPID velocityFPID = new FPID(
                Constants.ctreVoltageReslution / 21340f, // we can go 20k ticks per 100ms at full (1023) voltage
                0.1 * 1023 / 1000 / 4, //.01 * 1023 / 5000, // error of 5000 ticks
                0, // .001 our simulated motor is so perfect we don't need i or d
                0.1 * 1023 / 1000 / 4 * 10 // 10.0 * (0.1 * 1023 / 5000) // set d = 10 * p
        );

        // simulator
        leftMotors = new MotorSettings[] {
                MotorSettings.build(1)
                        .sensor(SensorType.Integrated)
                        .encoderTicks(2048)
                        .velocityFPID(velocityFPID),
                MotorSettings.build(2)
                        .sensor(SensorType.Integrated)
                        .encoderTicks(2048)
                        .follower(true)
        };

        rightMotors = new MotorSettings[] {
                MotorSettings.build(3)
                        .sensor(SensorType.Integrated)
                        .encoderTicks(2048)
                        .velocityFPID(velocityFPID)
                        .inverted(true)
                ,
                MotorSettings.build(4)
                        .sensor(SensorType.Integrated)
                        .encoderTicks(2048)
                        .inverted(true)
                        .follower(true)

        };

    }
}
