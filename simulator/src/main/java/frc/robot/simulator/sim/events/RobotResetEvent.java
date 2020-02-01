package frc.robot.simulator.sim.events;

public class RobotResetEvent extends SimEvent {

    public RobotResetEvent() {
        this.type = Type.RobotReset;
    }
}
