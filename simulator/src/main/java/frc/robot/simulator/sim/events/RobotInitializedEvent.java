package frc.robot.simulator.sim.events;

import frc.robot.simulator.network.proto.RobotProto;

public class RobotInitializedEvent extends RobotStateEvent {
    public RobotInitializedEvent(RobotProto.RobotState state) {
        super(state);
        this.type = Type.RobotInitialized;
    }
}
