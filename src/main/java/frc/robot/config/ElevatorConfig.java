package frc.robot.config;

import frc.robot.config.settings.FPID;
import frc.robot.config.settings.MotorSettings;

public class ElevatorConfig {

    FPID fpid = new FPID(
            1023/40000.0, // we can go 40k ticks per 100ms at full (1023) voltage
            0.1 * 1023 / 15000, // assume we have an error of 15k ticks
            0, // .001 our simulated motor is so perfect we don't need i or d
            0 // 10.0 * (0.1 * 1023 / 10000) // set d = 10 * p
    );

    // motor settings
    public MotorSettings elevatorMotor = MotorSettings.build(1).velocityFPID(fpid);
    public MotorSettings elevatorMotorFollower = MotorSettings.build(2).follower(true);

}
