package frc.robot.config;

import frc.robot.config.settings.FPID;
import frc.robot.config.settings.MotorSettings;
import frc.robot.config.settings.SensorType;

public class ArmConfig implements SimSupportingConfig {

    public MotorSettings[] armMotors = new MotorSettings[] {
            // these are junior settings
            MotorSettings.build(7)
                    .inverted(false)
                    .sensor(SensorType.Quadrature)
                    .encoderTicks(2048)
                    .velocityFPID(
                    0.114944,
                    0.683333 / 2 / 2,
                    0.0001,
                    10 * 0.683333 / 2 / 1.5
                    ),
            MotorSettings.build(8)
                    .sensor(SensorType.Quadrature)
                    .encoderTicks(2048)
                    .follower(true),
    };

    @Override
    public void overrideForSim() {

        FPID positionFPID = new FPID(
                0, // we can go 20k ticks per 100ms at full (1023) voltage
                (0.10 * Constants.ctreVoltageReslution) / Constants.ticksPerRevolution, // start with 10% power at one revolution off
                0, // .001 our simulated motor is so perfect we don't need i or d
                0 // 10.0 * (0.1 * 1023 / 10000) // set d = 10 * p
        );

        // simulator
        armMotors = new MotorSettings[] {
                MotorSettings.build(5)
                        .sensor(SensorType.Quadrature)
                        .encoderTicks(2048)
                        .positionFPID(positionFPID),
                MotorSettings.build(6)
                        .sensor(SensorType.Quadrature)
                        .encoderTicks(2048)
                        .follower(true)
        };

    }
}
