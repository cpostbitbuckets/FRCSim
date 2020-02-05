package frc.robot.simulator.sim.events;

public abstract class SimEvent {
    public enum Type {
        RobotInitialized,
        ConnectRobot,
        MotorOutputsUpdated,
        RobotReset,
        FieldRender,
    }

    public Type type;
}
