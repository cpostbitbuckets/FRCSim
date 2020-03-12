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

public class PS4Panel extends JPanel {
    private static final Logger log = LoggerFactory.getLogger(PS4Panel.class);
    private static final Color PRESSED_BTN_COLOR = new Color(0, 255, 0, 180);
    private static final Color JOYSTICK_BACKGROUND = new Color(204, 204, 204, 225);
    private static final float imageScale = .5f;

    private final Image controllerImage1;
    private final Image controllerImage2;
    private final int imageWidth;
    private final int imageHeight;

    public PS4Panel() throws IOException {
        // load in the xbox controller image to display on the background
        try (InputStream stream = PS4Panel.class.getResourceAsStream("/ps4_controller.png")) {
            controllerImage1 = ImageIO.read(stream);
        };

        try (InputStream stream = PS4Panel.class.getResourceAsStream("/ps4_controller2.png")) {
            controllerImage2 = ImageIO.read(stream);
        }
        imageWidth = (int)(controllerImage1.getWidth(null) * imageScale);
        imageHeight = (int)(controllerImage1.getHeight(null) * imageScale);
        setPreferredSize(new Dimension(imageWidth, imageHeight));
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
            graphics.drawImage(controllerImage2, 0, 0, imageWidth, imageHeight, null);
        } else {
            graphics.drawImage(controllerImage1, 0, 0, imageWidth, imageHeight, null);
        }

        colorButton(graphics, joystickData.buttons[PS4Constants.SQUARE.getValue() - 1], 440, 112);
        colorButton(graphics, joystickData.buttons[PS4Constants.CROSS.getValue() - 1], 484, 156);
        colorButton(graphics, joystickData.buttons[PS4Constants.CIRCLE.getValue() - 1], 528, 113);
        colorButton(graphics, joystickData.buttons[PS4Constants.TRIANGLE.getValue() - 1], 484, 70);
        colorBumper(graphics, joystickData.buttons[PS4Constants.L1.getValue() - 1], 103, 30);
        colorBumper(graphics, joystickData.buttons[PS4Constants.R1.getValue() - 1], 460, 30);

        colorButton(graphics, joystickData.buttons[PS4Constants.SHARE.getValue() - 1], 189, 67);
        colorButton(graphics, joystickData.buttons[PS4Constants.OPTIONS.getValue() - 1], 428, 67);
        colorButton(graphics, joystickData.buttons[PS4Constants.TRACKPAD.getValue() - 1], 303, 85);
        colorButton(graphics, joystickData.buttons[PS4Constants.PS4.getValue() - 1], 300, 195);

        drawJoystick(graphics, joystickData.buttons[PS4Constants.L_STICK.getValue() - 1], joystickData.axes[PS4Constants.LEFT_STICK_X.getValue()],
                joystickData.axes[PS4Constants.LEFT_STICK_Y.getValue()], 190, 181);
        drawJoystick(graphics, joystickData.buttons[PS4Constants.R_STICK.getValue() - 1], joystickData.axes[PS4Constants.RIGHT_STICK_X.getValue()],
                joystickData.axes[PS4Constants.RIGHT_STICK_Y.getValue()], 380, 181);

        drawTrigger(graphics, joystickData.axes[PS4Constants.LEFT_TRIGGER.getValue()], 105, 24);
        drawTrigger(graphics, joystickData.axes[PS4Constants.RIGHT_TRIGGER.getValue()], 459, 24);

        drawPOV(graphics, joystickData.povs);
    }

    private void drawPOV(Graphics graphics, int[] povs) // NOPMD
    {
        if (povs.length != 0) {
            int pov = povs[0];

            switch (pov) {
                case 0:
                    drawPOV(graphics, 118, 83);
                    break;
//                case 45:
//                    drawPOV(graphics, 250, 300);
//                    drawPOV(graphics, 288, 340);
//                    break;
                case 90:
                    drawPOV(graphics, 154, 115);
                    break;
//                case 135:
//                    drawPOV(graphics, 288, 340);
//                    drawPOV(graphics, 250, 365);
//                    break;
                case 180:
                    drawPOV(graphics, 118, 149);
                    break;
//                case 225:
//                    drawPOV(graphics, 250, 365);
//                    drawPOV(graphics, 210, 340);
//                    break;
                case 270:
                    drawPOV(graphics, 87, 115);
                    break;
//                case -45:
//                    drawPOV(graphics, 210, 340);
//                    drawPOV(graphics, 250, 300);
//                    break;
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
        graphics.fillRect((int) (x * imageScale), (int) (y * imageScale), (int) (40 * imageScale), (int) (40 * imageScale));
    }

    private void drawTrigger(Graphics graphics, double value, int x, int y) {
        Color color = UIUtils.alphaColor(UIUtils.colorGetShadedColor(value, 1, -1), 40);

        graphics.setColor(color);
        graphics.fillRect((int) (x * imageScale), (int) (y * imageScale), (int) (80 * imageScale), (int) (20 * imageScale));
    }

    private void drawJoystick(Graphics graphics, boolean pressed, double xAxis, double yAxis, int x, int y) {
        int width = (int) (75 * imageScale);
        int height = (int) (75 * imageScale);

        if (pressed) {
            graphics.setColor(PRESSED_BTN_COLOR);
        } else {
            graphics.setColor(JOYSTICK_BACKGROUND);
        }
        graphics.fillRect((int) (x * imageScale), (int) (y * imageScale), width, height);

        int xAxisSpot = (int) (xAxis * width * .5 + width * .5 + x * imageScale);
        int yAxisSpot = (int) (yAxis * height * .5 + height * .5 + y * imageScale);

        graphics.setColor(Color.black);
        graphics.fillOval(xAxisSpot, yAxisSpot, 5, 5);

    }

    private void colorBumper(Graphics graphics, boolean pressed, int x, int y) {
        if (pressed) {
            graphics.setColor(PRESSED_BTN_COLOR);
            graphics.fillRect((int) (x * imageScale), (int) (y * imageScale), (int) (80 * imageScale), (int) (20 * imageScale));
        }
    }

    private void colorButton(Graphics graphics, boolean pressed, int x, int y) {
        if (pressed) {
            graphics.setColor(PRESSED_BTN_COLOR);
            graphics.fillOval((int) (x * imageScale), (int) (y * imageScale), (int) (40 * imageScale), (int) (40 * imageScale));
        }
    }
}
