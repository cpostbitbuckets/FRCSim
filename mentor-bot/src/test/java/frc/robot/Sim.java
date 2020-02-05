package frc.robot;

import frc.robot.simulator.SimMain;
import frc.robot.simulator.network.proto.RobotProto;
import frc.robot.simulator.sim.RobotPosition;
import frc.robot.simulator.sim.events.EventManager;
import frc.robot.simulator.sim.events.FieldRenderEvent;
import frc.robot.simulator.sim.events.RobotInitializedEvent;

import java.awt.*;
import java.io.IOException;

public class Sim {
    public static void main(String[] args) throws InterruptedException, IOException {
        // create a new BitBuckets sim object to respond to simulator events
        Sim sim = new Sim();

        // subscribe to events from the simulator
        EventManager.subscribeToRobotInitializedEvents(sim::onRobotInitialized);
        EventManager.subscribeToMotorOutputsEvents(sim::onMotorOutputsUpdated);
        EventManager.subscribeToRobotPositionEvents(sim::onRobotPositionUpdated);
        EventManager.subscribeToFieldRenderEvents(sim::onFieldRender);

        // start the sim
        SimMain.main(args);
    }

    private void onFieldRender(FieldRenderEvent fieldRenderEvent) {
        if (robotInitialized) {
            Graphics2D g2d = fieldRenderEvent.getG2d();
            Rectangle robotRect = fieldRenderEvent.getRobotRect();

            int size = (int) (robotRect.width * .75);
            int x = robotRect.x + size / 4;
            int y = robotRect.y + size / 4;
            g2d.setColor(Color.RED);
            g2d.fillOval(x, y, size, size);
            g2d.setColor(Color.DARK_GRAY);
            g2d.drawOval(x, y, size, size);

            g2d.setColor(Color.YELLOW);
            int startX = x + size / 2;
            int startY = y + size / 2;
            int endX = startX + (int) (Math.cos(fieldRenderEvent.getHeading() - Math.PI / 2) * (robotRect.width / 2));
            int endY = startY + (int) (Math.sin(fieldRenderEvent.getHeading() - Math.PI / 2) * (robotRect.width / 2));

            g2d.drawLine(startX, startY, endX, endY);
        }
    }

    private void onRobotPositionUpdated(RobotPosition robotPosition) {
    }

    private void onMotorOutputsUpdated(RobotProto.MotorOutputs motorOutputs) {
    }

    private void onRobotInitialized(RobotInitializedEvent robotInitializedEvent) {
        robotInitialized = true;
    }

    private boolean robotInitialized;
}
