package frc.robot.config.settings;

public class JoystickButtons {
    public int leftStickX;
    public int leftStickY;
    public int rightStickX;
    public int rightStickY;
    public int leftTrigger;
    public int rightTrigger;

    public int square;
    public int cross;
    public int circle;
    public int triangle;

    public int l1;
    public int r1;
    public int l2;
    public int r2;

    public int lStickButton;
    public int rStickButton;

    public int brandButton;
    public int trackpad;

    public int share;
    public int option;

    public static JoystickButtons xbox() {
        var xbox = new JoystickButtons();
        xbox.leftStickX = XboxConstants.LEFT_STICK_X.getValue();
        xbox.leftStickY = XboxConstants.LEFT_STICK_Y.getValue();
        xbox.leftTrigger = XboxConstants.LEFT_TRIGGER.getValue();
        xbox.rightTrigger = XboxConstants.RIGHT_TRIGGER.getValue();
        xbox.rightStickX = XboxConstants.RIGHT_STICK_X.getValue();
        xbox.rightStickY = XboxConstants.RIGHT_STICK_Y.getValue();

        xbox.square = XboxConstants.X_BUTTON.getValue();
        xbox.cross = XboxConstants.A_BUTTON.getValue();
        xbox.circle = XboxConstants.B_BUTTON.getValue();
        xbox.triangle = XboxConstants.Y_BUTTON.getValue();

        xbox.l1 = XboxConstants.LB_BUTTON.getValue();
        xbox.r1 = XboxConstants.RB_BUTTON.getValue();
        // xbox has triggers, not buttons
//        xbox.l2 = XboxConstants.something.getValue();
//        xbox.r2 = XboxConstants.something.getValue();

        xbox.lStickButton = XboxConstants.L_STICK.getValue();
        xbox.rStickButton = XboxConstants.R_STICK.getValue();
        xbox.brandButton = XboxConstants.XBOX_BUTTON.getValue();

        xbox.trackpad = XboxConstants.TRACKPAD.getValue();
        return xbox;
    }

    public static JoystickButtons ps4() {
        var ps4 = new JoystickButtons();
        ps4.leftStickX = PS4Constants.LEFT_STICK_X.getValue();
        ps4.leftStickY = PS4Constants.LEFT_STICK_Y.getValue();
        ps4.leftTrigger = PS4Constants.LEFT_TRIGGER.getValue();
        ps4.rightTrigger = PS4Constants.RIGHT_TRIGGER.getValue();
        ps4.rightStickX = PS4Constants.RIGHT_STICK_X.getValue();
        ps4.rightStickY = PS4Constants.RIGHT_STICK_Y.getValue();

        ps4.square = PS4Constants.SQUARE.getValue();
        ps4.cross = PS4Constants.CROSS.getValue();
        ps4.circle = PS4Constants.CIRCLE.getValue();
        ps4.triangle = PS4Constants.TRIANGLE.getValue();

        ps4.l1 = PS4Constants.L1.getValue();
        ps4.r1 = PS4Constants.R1.getValue();
        ps4.l2 = PS4Constants.L2.getValue();
        ps4.r2 = PS4Constants.R2.getValue();

        ps4.lStickButton = PS4Constants.L_STICK.getValue();
        ps4.rStickButton = PS4Constants.R_STICK.getValue();
        ps4.brandButton = PS4Constants.PS4.getValue();

        ps4.trackpad = PS4Constants.TRACKPAD.getValue();

        ps4.share = PS4Constants.SHARE.getValue();
        ps4.option = PS4Constants.OPTIONS.getValue();

        return ps4;
    }

}
