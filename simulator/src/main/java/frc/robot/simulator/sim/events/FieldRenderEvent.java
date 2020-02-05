package frc.robot.simulator.sim.events;

import javax.swing.*;
import java.awt.*;

public class FieldRenderEvent extends SimEvent {

    final JPanel fieldPanel;
    final Graphics2D g2d;
    final Rectangle robotRect;
    final double heading;

    public FieldRenderEvent(JPanel fieldPanel, Graphics2D g2d, Rectangle robotRect, double heading) {
        this.fieldPanel = fieldPanel;
        this.g2d = g2d;
        this.robotRect = robotRect;
        this.heading = heading;
        this.type = Type.FieldRender;
    }

    public JPanel getFieldPanel() {
        return fieldPanel;
    }

    public Graphics2D getG2d() {
        return g2d;
    }

    public Rectangle getRobotRect() {
        return robotRect;
    }

    public double getHeading() {
        return heading;
    }
}
