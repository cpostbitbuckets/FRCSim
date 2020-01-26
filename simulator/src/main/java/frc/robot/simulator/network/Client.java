package frc.robot.simulator.network;

import frc.robot.simulator.network.proto.RobotProto;
import frc.robot.simulator.sim.JoystickData;

public interface Client {
    void shutdown() throws InterruptedException;

    void waitForServer();

    void updateMotor(RobotProto.MotorConfig motorConfig);

    void updateRobotState(RobotProto.RobotState robotState);

    void inputUpdate(JoystickData joystickData);
}
