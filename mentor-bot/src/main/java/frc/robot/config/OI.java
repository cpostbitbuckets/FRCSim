package frc.robot.config;

import frc.robot.config.settings.JoystickType;

public class OI {
    public int driverId = 0;
    public int operatorId = 1;

    JoystickType driver = JoystickType.xbox();
    JoystickType operator = JoystickType.ps4();

    public int armZeroButton = operator.square;
    public int armForwardButton = operator.cross;
    public int armBackwardButton = operator.circle;
    public int armResetButton = operator.triangle;

    public int shooterSpinUpButton = operator.r2;

    public int driveForwardAxis = driver.leftStickY;
    public int driveTurnAxis = driver.leftStickX;
    public int driveResetButton = driver.brandButton;
    public int driveSolenoidButton = driver.square;

}
