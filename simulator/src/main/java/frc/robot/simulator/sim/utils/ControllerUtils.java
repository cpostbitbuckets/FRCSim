package frc.robot.simulator.sim.utils;

import frc.robot.simulator.sim.ui.PS4Constants;
import frc.robot.simulator.sim.ui.XboxConstants;

public class ControllerUtils {

    public static int sdlPs4ToPs4Axis(int sdlPs4Axis) {
        // right stick y on the real joystick triggers the left trigger, swap them
        if (sdlPs4Axis == PS4Constants.RIGHT_STICK_Y.getValue()) {
            return PS4Constants.RIGHT_TRIGGER.getValue();
        }
        if (sdlPs4Axis == PS4Constants.RIGHT_TRIGGER.getValue()) {
            return PS4Constants.LEFT_TRIGGER.getValue();
        }
        if (sdlPs4Axis == PS4Constants.LEFT_TRIGGER.getValue()) {
            return PS4Constants.RIGHT_STICK_Y.getValue();
        }
        return sdlPs4Axis;
    }

    public static int xboxToPS4Axis(int xboxAxis) {
        if (xboxAxis == XboxConstants.LEFT_STICK_X.getValue()) {
            return PS4Constants.LEFT_STICK_X.getValue();
        }
        if (xboxAxis == XboxConstants.LEFT_STICK_Y.getValue()) {
            return PS4Constants.LEFT_STICK_Y.getValue();
        }
        if (xboxAxis == XboxConstants.RIGHT_STICK_X.getValue()) {
            return PS4Constants.RIGHT_STICK_X.getValue();
        }
        if (xboxAxis == XboxConstants.RIGHT_STICK_Y.getValue()) {
            return PS4Constants.RIGHT_STICK_Y.getValue();
        }
        if (xboxAxis == XboxConstants.LEFT_TRIGGER.getValue()) {
            return PS4Constants.LEFT_TRIGGER.getValue();
        }
        if (xboxAxis == XboxConstants.RIGHT_TRIGGER.getValue()) {
            return PS4Constants.RIGHT_TRIGGER.getValue();
        }

        return xboxAxis;
    }

    public static int xboxButtonToPS4Button(int xboxButton) {
        if (xboxButton == XboxConstants.A_BUTTON.getValue()) {
            return PS4Constants.CROSS.getValue();
        }
        if (xboxButton == XboxConstants.B_BUTTON.getValue()) {
            return PS4Constants.CIRCLE.getValue();
        }
        if (xboxButton == XboxConstants.X_BUTTON.getValue()) {
            return PS4Constants.SQUARE.getValue();
        }
        if (xboxButton == XboxConstants.Y_BUTTON.getValue()) {
            return PS4Constants.TRIANGLE.getValue();
        }
        if (xboxButton == XboxConstants.LB_BUTTON.getValue()) {
            return PS4Constants.L1.getValue();
        }
        if (xboxButton == XboxConstants.RB_BUTTON.getValue()) {
            return PS4Constants.R1.getValue();
        }
        if (xboxButton == XboxConstants.L_STICK.getValue()) {
            return PS4Constants.L_STICK.getValue();
        }
        if (xboxButton == XboxConstants.R_STICK.getValue()) {
            return PS4Constants.R_STICK.getValue();
        }
        if (xboxButton == XboxConstants.BACK_BUTTON.getValue()) {
            return PS4Constants.SHARE.getValue();
        }
        if (xboxButton == XboxConstants.START_BUTTON.getValue()) {
            return PS4Constants.OPTIONS.getValue();
        }
        if (xboxButton == XboxConstants.XBOX_BUTTON.getValue()) {
            return PS4Constants.PS4.getValue();
        }

        return xboxButton;
    }

    public static int sdlPs4ButtonToPS4Button(int sdlPs4Button) {
        if (sdlPs4Button == PS4Constants.CIRCLE.getValue()) {
            return PS4Constants.SQUARE.getValue();
        }
        if (sdlPs4Button == PS4Constants.CROSS.getValue()) {
            return PS4Constants.CIRCLE.getValue();
        }
        if (sdlPs4Button == PS4Constants.SQUARE.getValue()) {
            return PS4Constants.CROSS.getValue();
        }
        if (sdlPs4Button == PS4Constants.L1.getValue()) {
            return PS4Constants.SHARE.getValue();
        }
        if (sdlPs4Button == PS4Constants.OPTIONS.getValue()) {
            return PS4Constants.L1.getValue();
        }
        if (sdlPs4Button == PS4Constants.L_STICK.getValue()) {
            return PS4Constants.R1.getValue();
        }
        if (sdlPs4Button == PS4Constants.SHARE.getValue()) {
            return PS4Constants.R_STICK.getValue();
        }
        if (sdlPs4Button == PS4Constants.R1.getValue()) {
            return PS4Constants.PS4.getValue();
        }
        if (sdlPs4Button == PS4Constants.L2.getValue()) {
            return PS4Constants.OPTIONS.getValue();
        }
        if (sdlPs4Button == PS4Constants.R2.getValue()) {
            return PS4Constants.L_STICK.getValue();
        }
        if (sdlPs4Button == PS4Constants.R_STICK.getValue()) {
            return PS4Constants.DPAD_UP.getValue();
        }
        if (sdlPs4Button == PS4Constants.TRACKPAD.getValue()) {
            return PS4Constants.DPAD_LEFT.getValue();
        }
        if (sdlPs4Button == PS4Constants.PS4.getValue()) {
            return PS4Constants.DPAD_DOWN.getValue();
        }
        if (sdlPs4Button == PS4Constants.DPAD_UP.getValue()) {
            return PS4Constants.DPAD_RIGHT.getValue();
        }
        return sdlPs4Button;
    }
}
