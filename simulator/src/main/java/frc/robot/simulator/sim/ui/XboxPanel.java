package frc.robot.simulator.sim.ui;

import frc.robot.simulator.sim.JoystickData;
import frc.robot.simulator.sim.SimHAL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class XboxPanel extends JPanel {
    private static final Logger log = LoggerFactory.getLogger(XboxPanel.class);
    private static final Color PRESSED_BTN_COLOR = new Color(0, 255, 0, 180);
    private static final Color JOYSTICK_BACKGROUND = new Color(255, 255, 255, 180);

    private final Image controllerImage1;
    private final Image controllerImage2;

    public XboxPanel() throws IOException {
        // load in the xbox controller image to display on the background
        try (InputStream stream = XboxPanel.class.getResourceAsStream("/xbox_controller.png")) {
            controllerImage1 = ImageIO.read(stream);
        };

        try (InputStream stream = XboxPanel.class.getResourceAsStream("/xbox_controller2.png")) {
            controllerImage2 = ImageIO.read(stream);
        }
        setPreferredSize(new Dimension(controllerImage1.getWidth(null), controllerImage1.getHeight(null)));
    }

    @Override
    public void paint(Graphics graphics) {
        if (controllerImage1 == null) {
            return;
        }

        int activeJoystickId = SimWindowKeyListener.activeJoystickId;
        JoystickData joystickData = SimHAL.getJoystick(activeJoystickId);

        graphics.clearRect(0, 0, getWidth(), getHeight());
        graphics.setColor(getBackground());
        graphics.fillRect(0, 0, getWidth(), getHeight());
        if (activeJoystickId == 1) {
            graphics.drawImage(controllerImage2, 0, 0, null);
        } else {
            graphics.drawImage(controllerImage1, 0, 0, null);
        }

        colorButton(graphics, joystickData.buttons[XboxConstants.X_BUTTON.getValue() - 1], 490, 218);
        colorButton(graphics, joystickData.buttons[XboxConstants.Y_BUTTON.getValue() - 1], 550, 170);
        colorButton(graphics, joystickData.buttons[XboxConstants.B_BUTTON.getValue() - 1], 602, 215);
        colorButton(graphics, joystickData.buttons[XboxConstants.A_BUTTON.getValue() - 1], 540, 260);
        colorBumper(graphics, joystickData.buttons[XboxConstants.LB_BUTTON.getValue() - 1], 95, 95);
        colorBumper(graphics, joystickData.buttons[XboxConstants.RB_BUTTON.getValue() - 1], 510, 95);

        colorButton(graphics, joystickData.buttons[XboxConstants.BACK_BUTTON.getValue() - 1], 265, 220);
        colorButton(graphics, joystickData.buttons[XboxConstants.START_BUTTON.getValue() - 1], 415, 220);
        colorButton(graphics, joystickData.buttons[XboxConstants.XBOX_BUTTON.getValue() - 1], 340, 217);

        drawJoystick(graphics, joystickData.buttons[XboxConstants.L_STICK.getValue() - 1], joystickData.axes[XboxConstants.LEFT_STICK_X.getValue()],
                joystickData.axes[XboxConstants.LEFT_STICK_Y.getValue()], 115, 228);
        drawJoystick(graphics, joystickData.buttons[XboxConstants.R_STICK.getValue() - 1], joystickData.axes[XboxConstants.RIGHT_STICK_X.getValue()],
                joystickData.axes[XboxConstants.RIGHT_STICK_Y.getValue()], 420, 330);

        drawTrigger(graphics, joystickData.axes[XboxConstants.LEFT_TRIGGER.getValue()], 155, 40);
        drawTrigger(graphics, joystickData.axes[XboxConstants.RIGHT_TRIGGER.getValue()], 530, 40);

        drawPOV(graphics, joystickData.povs);
    }

    private void drawPOV(Graphics graphics, int[] povs) // NOPMD
    {
        if (povs.length != 0) {
            int pov = povs[0];

            switch (pov) {
                case 0:
                    drawPOV(graphics, 250, 300);
                    break;
                case 45:
                    drawPOV(graphics, 250, 300);
                    drawPOV(graphics, 288, 340);
                    break;
                case 90:
                    drawPOV(graphics, 288, 340);
                    break;
                case 135:
                    drawPOV(graphics, 288, 340);
                    drawPOV(graphics, 250, 365);
                    break;
                case 180:
                    drawPOV(graphics, 250, 365);
                    break;
                case 225:
                    drawPOV(graphics, 250, 365);
                    drawPOV(graphics, 210, 340);
                    break;
                case 270:
                    drawPOV(graphics, 210, 340);
                    break;
                case -45:
                    drawPOV(graphics, 210, 340);
                    drawPOV(graphics, 250, 300);
                    break;
                case -1:
                    break;
                default:
                    log.debug("Unexpected POV value: " + pov);
                    break;
            }
        }
    }

    private void drawPOV(Graphics graphics, int x, int y) {
        graphics.setColor(Color.red);
        graphics.fillRect(x, y, 40, 40);
    }

    private void drawTrigger(Graphics graphics, double value, int x, int y) {
        Color color = UIUtils.alphaColor(UIUtils.colorGetShadedColor(value, 1, -1), 40);

        graphics.setColor(color);
        graphics.fillRect(x, y, 60, 60);
    }

    private void drawJoystick(Graphics graphics, boolean pressed, double xAxis, double yAxis, int x, int y) {
        int width = 98;
        int height = 80;

        if (pressed) {
            graphics.setColor(PRESSED_BTN_COLOR);
        } else {
            graphics.setColor(JOYSTICK_BACKGROUND);
        }
        graphics.fillRect(x, y, width, height);

        int xAxisSpot = (int) (xAxis * width * .5 + width * .5 + x);
        int yAxisSpot = (int) (-yAxis * height * .5 + height * .5 + y);

        graphics.setColor(Color.black);
        graphics.fillOval(xAxisSpot, yAxisSpot, 5, 5);

    }

    private void colorBumper(Graphics graphics, boolean pressed, int x, int y) {
        if (pressed) {
            graphics.setColor(PRESSED_BTN_COLOR);
            graphics.fillRect(x, y, 140, 55);
        }
    }

    private void colorButton(Graphics graphics, boolean pressed, int x, int y) {
        if (pressed) {
            graphics.setColor(PRESSED_BTN_COLOR);
            graphics.fillOval(x, y, 60, 60);
        }
    }
}
