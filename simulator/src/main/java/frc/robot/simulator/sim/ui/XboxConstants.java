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
    LEFT_TRIGGER(3),
    RIGHT_TRIGGER(5),
    RIGHT_STICK_X(4),
    RIGHT_STICK_Y(2),

    // Buttons
    A_BUTTON(1),
    B_BUTTON(2),
    X_BUTTON(5),
    Y_BUTTON(3),
    LB_BUTTON(4),
    RB_BUTTON(5),
    BACK_BUTTON(9),
    START_BUTTON(6),
    L_STICK(10),
    R_STICK(7),
    XBOX_BUTTON(11),

    // D-PAD
    D_PAD_UP(12),
    D_PAD_RIGHT(14),
    D_PAD_DOWN(12),
    D_PAD_LEFT(13),
    D_PAD_NULL(-1);

    private int value;
    XboxConstants(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
