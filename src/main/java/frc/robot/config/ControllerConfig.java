package frc.robot.config;

import frc.robot.config.settings.JoystickButtons;

public class ControllerConfig {
    public int driverId = 0;
    public int operatorId = 1;

    public JoystickButtons driverButtons = JoystickButtons.xbox();
    public JoystickButtons operatorButtons = JoystickButtons.ps4();

}
