package frc.robot.simulator.sim.ui;

import frc.robot.simulator.network.proto.RobotProto;
import frc.robot.simulator.sim.RobotPosition;
import frc.robot.simulator.sim.motors.SimMotor;
import frc.robot.simulator.sim.motors.Vendor;
import frc.robot.simulator.sim.utils.ConversionUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.DecimalFormat;

class PositionDisplay extends JPanel {
    private static final DecimalFormat df = new DecimalFormat("#.##");

    JLabel xLabel;
    JLabel yLabel;
    JLabel velocityLabel;
    JLabel angularVelocityLabel;
    JLabel headingLabel;

    PositionDisplay() {
        JPanel labelsPanel = new JPanel();
        BoxLayout labelLayout = new BoxLayout(labelsPanel, BoxLayout.Y_AXIS);
        labelsPanel.setLayout(labelLayout);
        labelsPanel.setBorder(new EmptyBorder(10, 10, 0, 10));

        xLabel = new JLabel("X: ");
        yLabel = new JLabel("Y: ");
        velocityLabel = new JLabel("Vel: ");
        angularVelocityLabel = new JLabel("AngV: ");
        headingLabel = new JLabel("Heading: ");
        labelsPanel.add(xLabel);
        labelsPanel.add(yLabel);
        labelsPanel.add(velocityLabel);
        labelsPanel.add(angularVelocityLabel);
        labelsPanel.add(headingLabel);

        setLayout(new BorderLayout());
        add(labelsPanel, BorderLayout.CENTER);
    }


    public void update(RobotPosition robotPosition) {
        xLabel.setText("X: " + df.format(robotPosition.x) + " m");
        yLabel.setText("Y: " + df.format(robotPosition.y) + " m");
        velocityLabel.setText("Velocity: " + df.format(robotPosition.velocity) + " m/s");
        angularVelocityLabel.setText("Angular Velocity: " + df.format(robotPosition.angularVelocity * 360 / (Math.PI * 2)) + " degs/s");
        headingLabel.setText("Heading: " + df.format(robotPosition.heading * 360 / (Math.PI * 2)) + " degs");
    }

}