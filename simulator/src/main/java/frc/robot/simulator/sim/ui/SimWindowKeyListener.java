package frc.robot.simulator.sim.ui;

import frc.robot.simulator.network.Client;
import frc.robot.simulator.sim.JoystickData;
import frc.robot.simulator.sim.SimHAL;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class SimWindowKeyListener implements KeyListener {

    public static int activeJoystickId = 0;
    final Client inputClient;
    private JPanel panel;

    public SimWindowKeyListener(Client inputClient, JPanel controllerPanel) {
        this.inputClient = inputClient;
        this.panel = controllerPanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.isShiftDown()) {
            activeJoystickId = 1;
        } else {
            activeJoystickId = 0;
        }

        JoystickData joystickData = getJoystickData();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                joystickData.axes[PS4Constants.LEFT_STICK_Y.getValue()] = -1;
                break;
            case KeyEvent.VK_A:
                joystickData.axes[PS4Constants.LEFT_STICK_X.getValue()] = -1;
                break;
            case KeyEvent.VK_S:
                joystickData.axes[PS4Constants.LEFT_STICK_Y.getValue()] = 1;
                break;
            case KeyEvent.VK_D:
                joystickData.axes[PS4Constants.LEFT_STICK_X.getValue()] = 1;
                break;
            case KeyEvent.VK_I:
                joystickData.axes[PS4Constants.RIGHT_STICK_Y.getValue()] = -1;
                break;
            case KeyEvent.VK_J:
                joystickData.axes[PS4Constants.RIGHT_STICK_X.getValue()] = -1;
                break;
            case KeyEvent.VK_K:
                joystickData.axes[PS4Constants.RIGHT_STICK_Y.getValue()] = 1;
                break;
            case KeyEvent.VK_L:
                joystickData.axes[PS4Constants.RIGHT_STICK_X.getValue()] = 1;
                break;
            case KeyEvent.VK_COMMA:
                joystickData.axes[PS4Constants.LEFT_TRIGGER.getValue()] = 1;
                joystickData.buttons[PS4Constants.L2.getValue() - 1] = true;
                break;
            case KeyEvent.VK_PERIOD:
                joystickData.axes[PS4Constants.RIGHT_TRIGGER.getValue()] = 1;
                joystickData.buttons[PS4Constants.R2.getValue() - 1] = true;
                break;
            case KeyEvent.VK_1:
                joystickData.buttons[PS4Constants.SQUARE.getValue() - 1] = true;
                break;
            case KeyEvent.VK_2:
                joystickData.buttons[PS4Constants.CROSS.getValue() - 1] = true;
                break;
            case KeyEvent.VK_3:
                joystickData.buttons[PS4Constants.TRIANGLE.getValue() - 1] = true;
                break;
            case KeyEvent.VK_4:
                joystickData.buttons[PS4Constants.CIRCLE.getValue() - 1] = true;
                break;
            case KeyEvent.VK_5:
                joystickData.buttons[PS4Constants.L1.getValue() - 1] = true;
                break;
            case KeyEvent.VK_6:
                joystickData.buttons[PS4Constants.R1.getValue() - 1] = true;
                break;
            case KeyEvent.VK_7:
                joystickData.buttons[PS4Constants.SHARE.getValue() - 1] = true;
                break;
            case KeyEvent.VK_8:
                joystickData.buttons[PS4Constants.OPTIONS.getValue() - 1] = true;
                break;
            case KeyEvent.VK_9:
                joystickData.buttons[PS4Constants.TRACKPAD.getValue() - 1] = true;
                break;
            case KeyEvent.VK_0:
                joystickData.buttons[PS4Constants.L_STICK.getValue() - 1] = true;
                break;
            case KeyEvent.VK_MINUS:
                joystickData.buttons[PS4Constants.R_STICK.getValue() - 1] = true;
                break;
            case KeyEvent.VK_EQUALS:
                joystickData.buttons[PS4Constants.PS4.getValue() - 1] = true;
                break;
            case KeyEvent.VK_UP:
                joystickData.povs[0] = 0;
                break;
            case KeyEvent.VK_RIGHT:
                joystickData.povs[0] = 90;
                break;
            case KeyEvent.VK_DOWN:
                joystickData.povs[0] = 180;
                break;
            case KeyEvent.VK_LEFT:
                joystickData.povs[0] = 270;
                break;
        }

        // send a new package for the input
        inputClient.inputUpdate(joystickData);
        panel.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.isShiftDown()) {
            activeJoystickId = 1;
        } else {
            activeJoystickId = 0;
        }

        JoystickData joystickData = getJoystickData();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                joystickData.axes[PS4Constants.LEFT_STICK_Y.getValue()] = 0;
                break;
            case KeyEvent.VK_A:
                joystickData.axes[PS4Constants.LEFT_STICK_X.getValue()] = 0;
                break;
            case KeyEvent.VK_S:
                joystickData.axes[PS4Constants.LEFT_STICK_Y.getValue()] = 0;
                break;
            case KeyEvent.VK_D:
                joystickData.axes[PS4Constants.LEFT_STICK_X.getValue()] = 0;
                break;
            case KeyEvent.VK_I:
                joystickData.axes[PS4Constants.RIGHT_STICK_Y.getValue()] = 0;
                break;
            case KeyEvent.VK_J:
                joystickData.axes[PS4Constants.RIGHT_STICK_X.getValue()] = 0;
                break;
            case KeyEvent.VK_K:
                joystickData.axes[PS4Constants.RIGHT_STICK_Y.getValue()] = 0;
                break;
            case KeyEvent.VK_L:
                joystickData.axes[PS4Constants.RIGHT_STICK_X.getValue()] = 0;
                break;
            case KeyEvent.VK_COMMA:
                joystickData.axes[PS4Constants.LEFT_TRIGGER.getValue()] = -1;
                joystickData.buttons[PS4Constants.L2.getValue() - 1] = false;
                break;
            case KeyEvent.VK_PERIOD:
                joystickData.axes[PS4Constants.RIGHT_TRIGGER.getValue()] = -1;
                joystickData.buttons[PS4Constants.R2.getValue() - 1] = false;
                break;
            case KeyEvent.VK_1:
                joystickData.buttons[PS4Constants.SQUARE.getValue() - 1] = false;
                break;
            case KeyEvent.VK_2:
                joystickData.buttons[PS4Constants.CROSS.getValue() - 1] = false;
                break;
            case KeyEvent.VK_3:
                joystickData.buttons[PS4Constants.TRIANGLE.getValue() - 1] = false;
                break;
            case KeyEvent.VK_4:
                joystickData.buttons[PS4Constants.CIRCLE.getValue() - 1] = false;
                break;
            case KeyEvent.VK_5:
                joystickData.buttons[PS4Constants.L1.getValue() - 1] = false;
                break;
            case KeyEvent.VK_6:
                joystickData.buttons[PS4Constants.R1.getValue() - 1] = false;
                break;
            case KeyEvent.VK_7:
                joystickData.buttons[PS4Constants.SHARE.getValue() - 1] = false;
                break;
            case KeyEvent.VK_8:
                joystickData.buttons[PS4Constants.OPTIONS.getValue() - 1] = false;
                break;
            case KeyEvent.VK_9:
                joystickData.buttons[PS4Constants.TRACKPAD.getValue() - 1] = false;
                break;
            case KeyEvent.VK_0:
                joystickData.buttons[PS4Constants.L_STICK.getValue() - 1] = false;
                break;
            case KeyEvent.VK_MINUS:
                joystickData.buttons[PS4Constants.R_STICK.getValue() - 1] = false;
                break;
            case KeyEvent.VK_EQUALS:
                joystickData.buttons[PS4Constants.PS4.getValue() - 1] = false;
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_LEFT:
                joystickData.povs[0] = -1;
                break;
        }

        // if the spacebar key is released (i.e. pressed) switch joysticks
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            activeJoystickId = (activeJoystickId == 0 ? 1 : 0);
        }


        // send a new package for the input
        inputClient.inputUpdate(joystickData);
        panel.repaint();
    }

    public JoystickData getJoystickData() {
        return SimHAL.getJoystick(activeJoystickId);
    }
}
