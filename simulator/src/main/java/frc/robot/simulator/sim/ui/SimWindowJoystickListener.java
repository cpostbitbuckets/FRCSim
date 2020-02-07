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
import org.libsdl.SDL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.electronstudio.sdl2gdx.SDL2ControllerManager;

import javax.swing.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * This listens for controller button and axis presses. It also tries to map an
 * xbox controller's buttons and axis to a PS4 controller, if configured in the
 * sim
 */
public class SimWindowJoystickListener extends ControllerAdapter {
    private static final Logger log = LoggerFactory.getLogger(SimWindowJoystickListener.class);

    private static final Executor executor = Executors.newSingleThreadExecutor();

    final Client inputClient;
    final SimulatorConfig simulatorConfig;
    private JPanel panel;
    private boolean running = true;

    public SimWindowJoystickListener(Client inputClient, SimulatorConfig simulatorConfig, JPanel controllerPanel) {
        this.inputClient = inputClient;
        this.simulatorConfig = simulatorConfig;
        this.panel = controllerPanel;
    }

    /**
     * Setup the joystick listener. This just be called in order for the joystick
     * listener to work
     */
    public void setupJoysticks() {
        try {
            // stub out some fake libgdx stuff so we can register a controller listener
            Gdx.app = new LibGDXApplicationStub();
            Gdx.graphics = new LibGDXGraphicsStub(this.panel);
            Controllers.addListener(this);
        } catch (Exception e) {
            log.error("Failed to query for joysticks using libgdx, using SDL.", e);

            try {
                SDL.SDL_SetHint("SDL_HINT_XINPUT_ENABLED", "0");

                int result = SDL.SDL_GameControllerAddMapping("030000005e040000e002000003096800,Xbox Wireless Controller,a:b0,b:b1,back:b6,dpdown:h0.4,dpleft:h0.8,dpright:h0.2,dpup:h0.1,guide:b10,leftshoulder:b4,leftstick:b8,lefttrigger:a2,leftx:a0,lefty:a1,rightshoulder:b5,rightstick:b9,righttrigger:a5,rightx:a3,righty:a4,start:b7,x:b2,y:b3,platform:Mac OS X");
                SDL2ControllerManager controllerManager = new SDL2ControllerManager();
                if (result == -1) {
                    log.error("Failed to add joystick mapping: " + SDL.SDL_GetError());
                }
                controllerManager.addListener(this);

                // poll the joysticks
                executor.execute(() -> {
                    while (running) {
                        try {
                            controllerManager.pollState();
                        } catch (Exception pollException) {
                            log.error("Failed to poll controller.", pollException);
                        }
                    }
                });

            } catch (Exception sdlException) {
                log.error("Failed to query for joysticks.", sdlException);
            }
        }


    }

    @Override
    public boolean buttonDown(Controller controller, int buttonIndex) {
        JoystickData joystickData = getJoystickData();

//        log.info("Button " + (buttonIndex) +  " down");
        // If this is an xbox controller and we are configured for it, map it to a PS4
        // controller
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

//        log.info("Button " + (buttonIndex) +  " up");
        // If this is an xbox controller and we are configured for it, map it to a PS4
        // controller
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

//        log.info("axis " + (axisIndex) +  " value " + value);

        // If this is an xbox controller and we are configured for it, map it to a PS4
        // controller
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
     * 
     * @param controller
     * @param povIndex
     * @param value
     * @return
     */
    @Override
    public boolean povMoved(Controller controller, int povIndex, PovDirection value) {
        JoystickData joystickData = getJoystickData();

//        log.info("pov " + (povIndex) +  " value " + value);

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
     * Get the HAL simulated joystick data so we can update it TODO: Allow support
     * for multiple joysticks somehow...
     * 
     * @return
     */
    public JoystickData getJoystickData() {
        return SimHAL.getJoystick(SimWindowKeyListener.activeJoystickId);
    }

}
