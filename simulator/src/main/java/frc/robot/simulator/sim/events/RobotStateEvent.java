package frc.robot.simulator.sim.events;

import frc.robot.simulator.network.proto.RobotProto;

public abstract class RobotStateEvent extends SimEvent {
    public final RobotProto.RobotState state;

    public RobotStateEvent(RobotProto.RobotState state) {
        this.state = state;
    }
}
