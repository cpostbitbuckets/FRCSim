package frc.robot.simulator.sim;

public class RobotPosition {

    public enum Type {
        WheelDisplacement,
        Physics
    }

    public enum Color {
        Green,
        Red,
        Blue
    }

    public final Type type;
    public final Color color;
    public double velocity;
    public double angularVelocity;
    public double heading = 0;

    public double x;
    public double y;


    public RobotPosition(Type type, Color color) {
        this.type = type;
        this.color = color;
    }
}
