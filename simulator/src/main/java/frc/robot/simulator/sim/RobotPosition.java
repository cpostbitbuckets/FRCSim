package frc.robot.simulator.sim;

public class RobotPosition {

    public enum Type {
        WheelDisplacement,
    }

    public final Type type;
    public double velocity;
    public double angularVelocity;
    public double heading = 0;

    public double x;
    public double y;

    public RobotPosition(Type type) {
        this.type = type;
    }
}
