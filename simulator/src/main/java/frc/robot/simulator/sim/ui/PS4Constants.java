package frc.robot.simulator.sim.ui;

/**
 * Button Map for Xbox controller. When calling buttons or axis on an Xbox, use
 * this class.
 *
 * @author ayush_000
 *
 */
public enum PS4Constants
{
    // Axis
    LEFT_STICK_X(0),
    LEFT_STICK_Y(1),
    RIGHT_STICK_X(2),
    RIGHT_STICK_Y(5),
    LEFT_TRIGGER(3),
    RIGHT_TRIGGER(4),

    // Buttons
    SQUARE   (1),
    CROSS    (2),
    CIRCLE   (3),
    TRIANGLE (4),
    L1       (5),
    R1       (6),
    L2       (7),
    R2       (8),
    SHARE    (9),
    OPTIONS  (10),
    L_STICK  (11),
    R_STICK  (12),
    PS4      (13),
    TRACKPAD (14);


    private int value;
    PS4Constants(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
