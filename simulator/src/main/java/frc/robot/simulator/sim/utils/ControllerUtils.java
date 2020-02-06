package frc.robot.simulator.sim.utils;

import frc.robot.simulator.sim.ui.PS4Constants;
import frc.robot.simulator.sim.ui.XboxConstants;

public class ControllerUtils {

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

        return xboxButton;
    }
}
