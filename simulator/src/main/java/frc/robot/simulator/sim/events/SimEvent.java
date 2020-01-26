package frc.robot.simulator.sim.events;

public abstract class SimEvent {
    public enum Type {
        RobotInitialized,
        ConnectRobot,
        MotorOutputsUpdated,
    }

    public Type type;


}
