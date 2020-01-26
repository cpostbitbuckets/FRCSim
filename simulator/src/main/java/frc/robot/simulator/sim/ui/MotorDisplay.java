package frc.robot.simulator.sim.ui;

import frc.robot.simulator.network.proto.RobotProto;
import frc.robot.simulator.sim.motors.SimMotor;
import frc.robot.simulator.sim.motors.Vendor;
import frc.robot.simulator.sim.utils.ConversionUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.DecimalFormat;

class MotorDisplay extends JPanel {
    private static final DecimalFormat df = new DecimalFormat("#.##");

    /**
     * Helper class for displaying a colored ball for the motor speed
     */
    private static class MotorColorBallDisplay extends JPanel {
        private static final int DOT_SIZE = 30;
        private final Color background;

        double speed;

        MotorColorBallDisplay(Color background) {
            this.background = background;
            setPreferredSize(new Dimension(DOT_SIZE, DOT_SIZE));
        }

        @Override
        public void paint(Graphics g) {
            g.clearRect(0, 0, getWidth(), getHeight());
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(UIUtils.getMotorColor(speed));
            g.fillOval(0, 0, DOT_SIZE, DOT_SIZE);
        }
    }

    JLabel nameLabel;
    JLabel outputLabel;
    JLabel sensorPositionLabel;
    JLabel velocityLabel;
    JLabel targetLabel;
    MotorColorBallDisplay motorColorDisplay;

    final SimMotor motor;

    MotorDisplay(SimMotor motor) {
        this.motor = motor;

        JPanel motorColorPanel = new JPanel();
        motorColorPanel.setBorder(new EmptyBorder(10, 0, 0, 10));
        motorColorDisplay = new MotorColorBallDisplay(this.getBackground());
        motorColorDisplay.speed = 0;
        motorColorPanel.add(motorColorDisplay);

        JPanel labelsPanel = new JPanel();
        BoxLayout labelLayout = new BoxLayout(labelsPanel, BoxLayout.Y_AXIS);
        labelsPanel.setLayout(labelLayout);
        labelsPanel.setBorder(new EmptyBorder(10, 10, 0, 10));

        nameLabel = new JLabel("Name: " + this.motor.getConfig().getName());
        outputLabel = new JLabel("Out: ");
        sensorPositionLabel = new JLabel("Pos: ");
        velocityLabel = new JLabel("Vel: ");
        targetLabel = new JLabel("Target: <none>");

        labelsPanel.add(nameLabel);
        labelsPanel.add(outputLabel);
        labelsPanel.add(sensorPositionLabel);
        labelsPanel.add(velocityLabel);
        labelsPanel.add(targetLabel);

        setLayout(new BorderLayout());

        add(labelsPanel, BorderLayout.CENTER);
        add(motorColorPanel, BorderLayout.LINE_END);

    }


    public void update(RobotProto.MotorOutputs.MotorOutput output) {
        // we don't use deltaTime, but update the display
        motorColorDisplay.speed = output.getVelocity();
        outputLabel.setText("Out: " + df.format(output.getOutput()));

        if (motor.getVendor() == Vendor.CTRE) {
            int ticksPerRevolution = ConversionUtils.ticksPerRevolution(motor.getConfig().getEncoderCountsPerRevolution(), motor.getConfig().getEncoder());
            sensorPositionLabel.setText("Sensor Pos: " + df.format(ConversionUtils.radiansToTicks(output.getSensorPosition(), ticksPerRevolution)) + " ticks");
        } else if (motor.getVendor() == Vendor.Rev) {
            sensorPositionLabel.setText("Sensor Pos: " + df.format(ConversionUtils.radiansToRevolutions(output.getSensorPosition())) + " revs");
        }

        if (motor.getVendor() == Vendor.CTRE) {
            int ticksPerRevolution = ConversionUtils.ticksPerRevolution(motor.getConfig().getEncoderCountsPerRevolution(), motor.getConfig().getEncoder());
            velocityLabel.setText("Vel: " + df.format(ConversionUtils.radiansPerSecondToTicksPer100ms(output.getVelocity(), ticksPerRevolution)) + " ticks/100ms");
        } else if (motor.getVendor() == Vendor.Rev) {
            velocityLabel.setText("Vel: " + df.format(ConversionUtils.radiansPerSecondToRPM(output.getVelocity())) + " RPM");
        }
    }

    public void update(RobotProto.MotorConfig config) {

        motor.setConfig(config);
        nameLabel.setText("Name: " + config.getName());

        RobotProto.MotorConfig.ControlMode mode = config.getControlMode();
        switch (mode) {

            case PercentOutput:
                targetLabel.setText("Target Out: " + df.format(config.getTargetOutput()));
                break;
            case MotionPosition:
            case Position:
                if (config.getVendor() == RobotProto.MotorConfig.Vendor.CTRE) {
                    int ticksPerRevolution = ConversionUtils.ticksPerRevolution(config.getEncoderCountsPerRevolution(), config.getEncoder());
                    targetLabel.setText("Target Pos: " + df.format(ConversionUtils.radiansToTicks(config.getTargetPosition(), ticksPerRevolution)) + " ticks");
                } else if (config.getVendor() == RobotProto.MotorConfig.Vendor.Rev) {
                    targetLabel.setText("Target Pos: " + df.format(ConversionUtils.radiansToRevolutions(config.getTargetPosition())) + " revs");
                }
                break;
            case Velocity:
            case MotionVelocity:
                if (config.getVendor() == RobotProto.MotorConfig.Vendor.CTRE) {
                    int ticksPerRevolution = ConversionUtils.ticksPerRevolution(config.getEncoderCountsPerRevolution(), config.getEncoder());
                    targetLabel.setText("Target Vel: " + df.format(ConversionUtils.radiansPerSecondToTicksPer100ms(config.getTargetVelocity(), ticksPerRevolution)) + " ticks/100ms");
                } else if (config.getVendor() == RobotProto.MotorConfig.Vendor.Rev) {
                    targetLabel.setText("Target Vel: " + df.format(ConversionUtils.radiansPerSecondToRPM(config.getTargetVelocity())) + " RPM");
                }
                break;
            case Current:
            case Follower:
            case MotionProfile:
            case MotionProfileArc:
            case Disabled:
                targetLabel.setText("Target: <none>");
                break;
        }
    }
}
