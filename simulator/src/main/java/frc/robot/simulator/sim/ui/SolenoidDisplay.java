package frc.robot.simulator.sim.ui;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.simulator.sim.solenoids.SimSolenoidPort;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

class SolenoidDisplay extends JPanel {

    /**
     * Helper class for displaying a colored ball for the motor speed
     */
    private static class SolenoidColorBallDisplay extends JPanel {
        private static final int DOT_SIZE = 30;
        private final Color background;

        DoubleSolenoid.Value state;

        SolenoidColorBallDisplay(Color background) {
            this.background = background;
            setPreferredSize(new Dimension(DOT_SIZE, DOT_SIZE));
        }

        @Override
        public void paint(Graphics g) {
            g.clearRect(0, 0, getWidth(), getHeight());
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
            switch (state) {
                case kOff:
                    g.setColor(UIUtils.getMotorColor(0));
                    break;
                case kForward:
                    g.setColor(UIUtils.getMotorColor(1));
                    break;
                case kReverse:
                    g.setColor(UIUtils.getMotorColor(-1));
                    break;
            }
            g.fillOval(0, 0, DOT_SIZE, DOT_SIZE);
        }
    }

    JLabel nameLabel;
    JLabel channelLabel;
    JLabel stateLabel;
    SolenoidColorBallDisplay solenoidColorBallDisplay;

    final SimSolenoidPort solenoid;

    SolenoidDisplay(SimSolenoidPort solenoid) {
        this.solenoid = solenoid;

        JPanel solenoidColorPanel = new JPanel();
        solenoidColorPanel.setBorder(new EmptyBorder(10, 0, 0, 10));
        solenoidColorBallDisplay = new SolenoidColorBallDisplay(this.getBackground());
        solenoidColorBallDisplay.state = DoubleSolenoid.Value.kOff;
        solenoidColorPanel.add(solenoidColorBallDisplay);

        JPanel labelsPanel = new JPanel();
        BoxLayout labelLayout = new BoxLayout(labelsPanel, BoxLayout.Y_AXIS);
        labelsPanel.setLayout(labelLayout);
        labelsPanel.setBorder(new EmptyBorder(10, 10, 0, 10));

        nameLabel = new JLabel(this.solenoid.name);
        channelLabel = new JLabel("Channel: " + this.solenoid.channel);
        stateLabel = new JLabel("State: " + this.solenoid.state.toString());

        labelsPanel.add(nameLabel);
        labelsPanel.add(channelLabel);
        labelsPanel.add(stateLabel);

        setLayout(new BorderLayout());

        add(labelsPanel, BorderLayout.CENTER);
        add(solenoidColorPanel, BorderLayout.LINE_END);
    }


    public void update(SimSolenoidPort solenoid) {
        // we don't use deltaTime, but update the display
        solenoidColorBallDisplay.state = solenoid.state;
        stateLabel.setText("State: " + this.solenoid.state.toString());
    }
}
