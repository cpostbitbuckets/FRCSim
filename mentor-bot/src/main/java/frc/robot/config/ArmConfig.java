package frc.robot.config;

import frc.robot.config.settings.FPID;
import frc.robot.config.settings.MotorSettings;
import frc.robot.config.settings.SensorType;

public class ArmConfig extends SubsystemConfig {
    public ArmConfig() {
        enabled = false;
    }

    FPID positionFPID = new FPID(
            0, // we can go 20k ticks per 100ms at full (1023) voltage
            (0.10 * Constants.ctreVoltageReslution) / Constants.ticksPerRevolution, // start with 10% power at one revolution off
            0, // .001 our simulated motor is so perfect we don't need i or d
            0 // 10.0 * (0.1 * 1023 / 10000) // set d = 10 * p
    );

    public MotorSettings[]         // simulator
            armMotors = new MotorSettings[] {
            MotorSettings.build(Constants.ARM_IDS[0])
                    .sensor(SensorType.Quadrature)
                    .encoderTicks(2048)
                    .positionFPID(positionFPID),
            MotorSettings.build(Constants.ARM_IDS[1])
                    .sensor(SensorType.Quadrature)
                    .encoderTicks(2048)
                    .follower(true)
    };

}
