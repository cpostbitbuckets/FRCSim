package frc.robot.config;

public class Config {
    // all motors use the same slot id for motion magic vs velocity.
    public int positionSlotIndex = 0;
    public int velocitySlotIndex = 1;

    public ControllerConfig controllerConfig = new ControllerConfig();
    public ElevatorConfig elevatorConfig = new ElevatorConfig();

    // timeout for doing various talon config operations
    public int ctreTimeout = 30;

    public Config() {
    }

}
