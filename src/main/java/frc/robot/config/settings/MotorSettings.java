package frc.robot.config.settings;

public class MotorSettings {
    public final int id;
    // some subsystems (like the drive subsystem) ignore this and
    // configure any motor but the first one to be a follower
    public boolean follower = false;
    public boolean inverted = false;
    public boolean sensorPhase = true;

    public FPID velocityFPID = new FPID();
    public FPID positionFPID = new FPID();

    // 0 for primary pid index. TODO: Figure out what this does
    public int pidIndex = 0;

    public MotorSettings(int id) {
        this.id = id;
    }

    public static MotorSettings build(int id) {
        return new MotorSettings(id);
    }

    public MotorSettings follower(boolean follower) {
        this.follower = follower;
        return this;
    }

    public MotorSettings inverted(boolean inverted) {
        this.inverted = inverted;
        return this;
    }

    public MotorSettings velocityFPID(FPID fpid) {
        this.velocityFPID.set(fpid);
        return this;
    }

    public MotorSettings velocityFPID(double f, double p, double i, double d) {
        this.velocityFPID.set(f, p, i, d);
        return this;
    }

    public MotorSettings positionFPID(FPID fpid) {
        this.positionFPID.set(fpid);
        return this;
    }

}
