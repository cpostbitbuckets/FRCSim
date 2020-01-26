package frc.robot.simulator.network;

import frc.robot.simulator.network.proto.RobotProto;
import frc.robot.simulator.sim.JoystickData;
import frc.robot.simulator.sim.SimulatorThread;
import frc.robot.simulator.sim.events.EventManager;
import frc.robot.simulator.sim.events.RobotInitializedEvent;
import frc.robot.simulator.sim.motors.MotorSimulator;
import frc.robot.simulator.sim.motors.MotorStore;

public class LocalClient implements Client {
    private final SimulatorThread simulatorThread;
    private final MotorStore motorStore;

    public LocalClient(SimulatorThread simulatorThread, MotorStore motorStore) {
        this.simulatorThread = simulatorThread;
        this.motorStore = motorStore;
        EventManager.subscribeToRobotInitializedEvents(this::onRobotInitialized);
    }

    private void onRobotInitialized(RobotInitializedEvent robotInitializedEvent) {
        simulatorThread.startSimulationThread();
    }

    @Override
    public void shutdown() throws InterruptedException {
        simulatorThread.stopSimulationThread();
    }

    @Override
    public void waitForServer() {
        // ignore, we are always ready, no server
    }

    @Override
    public void updateMotor(RobotProto.MotorConfig motorConfig) {
//        motorStore.updateMotorConfig(motorConfig);
        EventManager.publish(motorConfig);
    }

    @Override
    public void updateRobotState(RobotProto.RobotState robotState) {
        // ignore, the HAL will already have the latest state data
    }

    @Override
    public void inputUpdate(JoystickData joystickData) {
        // ignore, the HAL will already have the latest input data
    }
}
