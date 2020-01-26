package frc.robot.simulator.sim.ui;

import frc.robot.simulator.sim.SimHAL;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class RobotStatePanel extends JPanel {
    private final JCheckBox enableButton = new JCheckBox("Enabled");
    private final JCheckBox teleopButton = new JCheckBox("Teleop");
    private final JCheckBox autonomousButton = new JCheckBox("Autonomous");
    private final JCheckBox testButton = new JCheckBox("Test");
    private final JLabel timeLabel = new JLabel("Time: 000.00");

    public RobotStatePanel() {

        add(enableButton);
        add(autonomousButton);
        add(teleopButton);
        add(testButton);
        add(timeLabel);

        ActionListener actionListener = event -> {
            // can't be in autonomous and test
            if (event.getSource() == autonomousButton) {
                testButton.setSelected(false);
                teleopButton.setSelected(false);
            } else if (event.getSource() == testButton) {
                autonomousButton.setSelected(false);
                teleopButton.setSelected(false);
            } else if (event.getSource() == teleopButton) {
                autonomousButton.setSelected(false);
                testButton.setSelected(false);
            }

            SimHAL.getDriverStation().setEnabled(enableButton.isSelected());
            SimHAL.getDriverStation().setAutonomous(autonomousButton.isSelected());
            SimHAL.getDriverStation().setTest(testButton.isSelected());
        };

        enableButton.addActionListener(actionListener);
        autonomousButton.addActionListener(actionListener);
        teleopButton.addActionListener(actionListener);
        testButton.addActionListener(actionListener);

        enableButton.setSelected(SimHAL.getDriverStation().isEnabled());
        teleopButton.setSelected(!SimHAL.getDriverStation().isAutonomous() && !SimHAL.getDriverStation().isTest());
        autonomousButton.setSelected(SimHAL.getDriverStation().isAutonomous());
        testButton.setSelected(SimHAL.getDriverStation().isTest());
    }

    public void setTime(double aTime) {
        DecimalFormat df = new DecimalFormat("000.00");
        timeLabel.setText("Time: " + df.format(aTime));
    }

    public void update() {
        setTime(SimHAL.getDriverStation().getLastTimeEnabled());
    }
}
