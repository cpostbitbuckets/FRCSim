package frc.robot.simulator.sim.ui;

/**
 * Button Map for Xbox controller. When calling buttons or axis on an Xbox, use
 * this class.
 *
 * @author ayush_000
 *
 */
public enum XboxConstants
{
    // Axis
    LEFT_STICK_X(0),
    LEFT_STICK_Y(1),
    LEFT_TRIGGER(2),
    RIGHT_TRIGGER(3),
    RIGHT_STICK_X(4),
    RIGHT_STICK_Y(5),

    // Buttons
    A_BUTTON(1),
    B_BUTTON(2),
    X_BUTTON(3),
    Y_BUTTON(4),
    LB_BUTTON(5),
    RB_BUTTON(6),
    BACK_BUTTON(7),
    START_BUTTON(8),
    L_STICK(9),
    R_STICK(10),
    XBOX_BUTTON(11),
    TRACKPAD(11),

    // D-PAD
    D_PAD_UP(0),
    D_PAD_RIGHT(90),
    D_PAD_DOWN(180),
    D_PAD_LEFT(270),
    D_PAD_NULL(-1);

    private int value;
    XboxConstants(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
