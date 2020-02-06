package frc.robot.simulator.sim.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import frc.robot.simulator.network.Client;
import frc.robot.simulator.sim.JoystickData;
import frc.robot.simulator.sim.SimHAL;
import frc.robot.simulator.sim.config.SimulatorConfig;
import frc.robot.simulator.sim.libgdx.LibGDXApplicationStub;
import frc.robot.simulator.sim.libgdx.LibGDXGraphicsStub;
import frc.robot.simulator.sim.utils.ControllerUtils;

import javax.swing.*;

/**
 * This listens for controller button and axis presses. It also tries to
 * map an xbox controller's buttons and axis to a PS4 controller, if configured in the sim
 */
public class SimWindowJoystickListener extends ControllerAdapter {

    final Client inputClient;
    final SimulatorConfig simulatorConfig;
    private JPanel panel;

    public SimWindowJoystickListener(Client inputClient, SimulatorConfig simulatorConfig, JPanel controllerPanel) {
        this.inputClient = inputClient;
        this.simulatorConfig = simulatorConfig;
        this.panel = controllerPanel;
    }

    /**
     * Setup the joystick listener. This just be called in order for the joystick listener to work
     */
    public void setupJoysticks() {
        // stub out some fake libgdx stuff so we can register a controller listener
        Gdx.app = new LibGDXApplicationStub();
        Gdx.graphics = new LibGDXGraphicsStub();

        Controllers.addListener(this);
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonIndex) {
        JoystickData joystickData = getJoystickData();

        // If this is an xbox controller and we are configured for it, map it to a PS4 controller
        if (simulatorConfig.remapXboxController && controller.getName().toLowerCase().contains("xbox")) {
            joystickData.buttons[ControllerUtils.xboxButtonToPS4Button(buttonIndex + 1) - 1] = true;
        } else {
            joystickData.buttons[buttonIndex] = true;
        }
        // send a new package for the input
        inputClient.inputUpdate(joystickData);
        panel.repaint();

        return true;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonIndex) {
        JoystickData joystickData = getJoystickData();

        // If this is an xbox controller and we are configured for it, map it to a PS4 controller
        if (simulatorConfig.remapXboxController && controller.getName().toLowerCase().contains("xbox")) {
            joystickData.buttons[ControllerUtils.xboxButtonToPS4Button(buttonIndex + 1) - 1] = false;
        } else {
            joystickData.buttons[buttonIndex] = false;
        }
        // send a new package for the input
        inputClient.inputUpdate(joystickData);
        panel.repaint();

        return true;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisIndex, float value) {
        JoystickData joystickData = getJoystickData();

        // If this is an xbox controller and we are configured for it, map it to a PS4 controller
        if (simulatorConfig.remapXboxController && controller.getName().toLowerCase().contains("xbox")) {
            int ps4Axis = ControllerUtils.xboxToPS4Axis(axisIndex);
            joystickData.axes[ps4Axis] = value;

            // The ps4 returns -1 for the joystick up on the y sticks
            if (ps4Axis == PS4Constants.LEFT_STICK_Y.getValue()) {
                joystickData.axes[ps4Axis] = -value;
            }
            if (ps4Axis == PS4Constants.RIGHT_STICK_Y.getValue()) {
                joystickData.axes[ps4Axis] = -value;
            }

            // The ps4 registers an additional button press if the user hits the triggers
            if (ps4Axis == PS4Constants.LEFT_TRIGGER.getValue()) {
                if (value > -.8) {
                    joystickData.buttons[PS4Constants.L2.getValue() - 1] = true;
                } else {
                    joystickData.buttons[PS4Constants.L2.getValue() - 1] = false;
                }
            }
            if (ps4Axis == PS4Constants.RIGHT_TRIGGER.getValue()) {
                if (value > -.8) {
                    joystickData.buttons[PS4Constants.R2.getValue() - 1] = true;
                } else {
                    joystickData.buttons[PS4Constants.R2.getValue() - 1] = false;
                }
            }
        } else {
            joystickData.axes[axisIndex] = value;
        }

        // send a new package for the input
        inputClient.inputUpdate(joystickData);
        panel.repaint();

        return true;
    }

    /**
     * Note: Not working on an xbox controller on a mac...
     * @param controller
     * @param povIndex
     * @param value
     * @return
     */
    @Override
    public boolean povMoved(Controller controller, int povIndex, PovDirection value) {
        JoystickData joystickData = getJoystickData();

        switch (value) {

            case center:
                joystickData.povs[povIndex] = -1;
                break;
            case north:
                joystickData.povs[povIndex] = 0;
                break;
            case south:
                joystickData.povs[povIndex] = 180;
                break;
            case east:
                joystickData.povs[povIndex] = 90;
                break;
            case west:
                joystickData.povs[povIndex] = 270;
                break;
            case northEast:
                joystickData.povs[povIndex] = 45;
                break;
            case southEast:
                joystickData.povs[povIndex] = 135;
                break;
            case northWest:
                joystickData.povs[povIndex] = 315;
                break;
            case southWest:
                joystickData.povs[povIndex] = 225;
                break;
        }
        // send a new package for the input
        inputClient.inputUpdate(joystickData);
        panel.repaint();

        return true;
    }

    /**
     * Get the HAL simulated joystick data so we can update it
     * TODO: Allow support for multiple joysticks somehow...
     * @return
     */
    public JoystickData getJoystickData() {
        return SimHAL.getJoystick(SimWindowKeyListener.activeJoystickId);
    }

}
