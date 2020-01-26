package frc.robot.simulator.sim.ui;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import frc.robot.simulator.network.Client;
import frc.robot.simulator.network.proto.RobotProto;
import frc.robot.simulator.sim.RobotPosition;
import frc.robot.simulator.sim.config.SimulatorConfig;
import frc.robot.simulator.sim.events.EventManager;
import frc.robot.simulator.sim.motors.SimMotor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
public class SimWindow {
    private static final Logger log = LoggerFactory.getLogger(SimWindow.class);

    private final SimulatorConfig simulatorConfig;
    private final Client inputClient;

    private JFrame frame;
    private GridLayout motorGridLayout = new GridLayout(1, 4, 20, 20);
    private JPanel motorsPanel;
    private PS4Panel ps4Panel;
    private FieldPanel fieldPanel;
    private SimWindowKeyListener keyListener;
    private RobotStatePanel robotStatePanel;
    private PositionDisplay positionDisplay = new PositionDisplay();

    Map<Integer, MotorDisplay> motorDisplaysById = new HashMap<>();

    long lastOutputUpdateTime = 0;
    long lastPositionUpdateTime = 0;
    boolean created = false;

    @Inject
    public SimWindow(SimulatorConfig simulatorConfig, @Named("InputClient") Client inputClient) {
        this.simulatorConfig = simulatorConfig;
        this.inputClient = inputClient;
    }

    public void create(List<SimMotor> motors) throws IOException {

        if (created) {
            // only create the UI once
            // this gets called after every robot initialize, even if the server starts and stops
            return;
        }
        // build the UI
        frame = new JFrame();
        frame.setTitle("FRCSim");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        robotStatePanel = new RobotStatePanel();

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(new MatteBorder(0, 0, 1, 0, Color.darkGray));
        topPanel.add(robotStatePanel);

        JPanel centerPanel = new JPanel();

        // create a panel for showing motor outputs and add it to the frame
        motorsPanel = new JPanel();
        motorsPanel.setLayout(motorGridLayout);
        motorsPanel.setBorder(new EmptyBorder(10, 0, 10, 0));

        // create a ps4 controller panel for showing user inputs and add it to the frame
        JPanel controllerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ps4Panel = new PS4Panel();
        controllerWrapper.add(ps4Panel);
        controllerWrapper.setBorder(new MatteBorder(1, 0, 0, 0, Color.darkGray));

        JPanel fieldWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        fieldPanel = new FieldPanel();
        fieldWrapper.add(fieldPanel);
        fieldWrapper.setBorder(new MatteBorder(0, 1, 0, 0, Color.darkGray));

        // we want a row for every 3 motors
        if (motors.size() > 0) {
            int numDisplayedMotors = motors.size();
            if (simulatorConfig.hideFollowers) {
                numDisplayedMotors = (int) motors.stream().filter(m -> m.getConfig().getFollowingId() == 0).count();
            }
            motorGridLayout.setRows(numDisplayedMotors / 4 + 1);
        }

        for (SimMotor simMotor : motors) {
            if (simulatorConfig.hideFollowers && simMotor.getConfig().getFollowingId() != 0) {
                // hideFollowers is set, don't show follow motors
                continue;
            }
            createMotorDisplay(motorsPanel, simMotor);
        }

        positionDisplay = new PositionDisplay();
        motorsPanel.add(positionDisplay);

        JScrollPane middlePanel = new JScrollPane(motorsPanel);

        centerPanel.add(middlePanel, BorderLayout.CENTER);
        centerPanel.add(controllerWrapper, BorderLayout.SOUTH);

        final Container container = frame.getContentPane();
        container.setLayout(new BorderLayout());
        container.add(topPanel, BorderLayout.NORTH);
        container.add(centerPanel, BorderLayout.CENTER);
        container.add(fieldWrapper, BorderLayout.EAST);


        frame.pack();
        frame.setSize(frame.getWidth() + 50, frame.getHeight() + 100);
        frame.setVisible(true);

        // create a key listener to listen for keyboard events
        keyListener = new SimWindowKeyListener(inputClient, ps4Panel);
        frame.addKeyListener(keyListener);
        frame.setFocusable(true);
        frame.setFocusTraversalKeysEnabled(false);

        // subscribe to motor output events
        EventManager.subscribeToMotorOutputsEvents(this::onMotorOutputsEvent);
        EventManager.subscribeToMotorConfigEvents(this::onMotorConfigEvent);
        EventManager.subscribeToRobotPositionEvents(this::onRobotPositionEvent);

        created = true;
    }

    private void onMotorConfigEvent(RobotProto.MotorConfig motorConfig) {
        MotorDisplay display = motorDisplaysById.get(motorConfig.getId());
        if (display != null) {
            display.update(motorConfig);
            display.repaint();
        }
    }

    /**
     * This will add a MotorDisplay component to a container (probably mainPanel)
     *
     * @param pane
     * @param simMotor   The simulated motor to create a display for
     */
    private void createMotorDisplay(Container pane, SimMotor simMotor) {
        MotorDisplay motorDisplay = new MotorDisplay(simMotor);
        motorDisplaysById.put(simMotor.getId(), motorDisplay);

        // add the motor display to the pane
        pane.add(motorDisplay);
    }

    /**
     * Update the motor displays with new outputs
     *
     * @param outputs
     */
    private void updateMotorOutputs(RobotProto.MotorOutputs outputs) {
        for (RobotProto.MotorOutputs.MotorOutput output : outputs.getMotorOutputList()) {
            MotorDisplay display = motorDisplaysById.get(output.getId());
            // this could be null because we are hiding followers
            if (display != null) {
                display.update(output);
                display.repaint();
            }
        }
        this.ps4Panel.repaint();
        this.robotStatePanel.update();
    }


    /**
     * Called when a SimEvent happens.
     *
     * @param motorOutputs
     */
    public void onMotorOutputsEvent(final RobotProto.MotorOutputs motorOutputs) {
        long deltaTime = System.currentTimeMillis() - lastOutputUpdateTime;
        if (deltaTime > 100) {
            // we have new motor outputs, so update the motor displays
            java.awt.EventQueue.invokeLater(() -> {
                for (RobotProto.MotorOutputs.MotorOutput output : motorOutputs.getMotorOutputList()
                        .stream()
                        .sorted(Comparator.comparingInt(m -> m.getId()))
                        .collect(Collectors.toList())) {
                }

                updateMotorOutputs(motorOutputs);
            });

            lastOutputUpdateTime = System.currentTimeMillis();
        }
    }

    public void onRobotPositionEvent(final RobotPosition robotPosition) {
        long deltaTime = System.currentTimeMillis() - lastPositionUpdateTime;
//        if (deltaTime >= 20) {
            // we have new motor outputs, so update the motor displays
            java.awt.EventQueue.invokeLater(() -> {
                if (robotPosition.type == RobotPosition.Type.WheelDisplacement) {
                    positionDisplay.update(robotPosition);
                    positionDisplay.repaint();
                }
                fieldPanel.update(robotPosition);
                fieldPanel.repaint();
            });
            lastPositionUpdateTime = System.currentTimeMillis();
//        }
    }


}
