package frc.robot.simulator.sim;

/**
 * Class to store joystick data. This will be updated by our clients
 */
public class JoystickData {
    // the id of the joystick
    public int id;

    public float[] axes = new float[] {
            0,
            0,
            0,
            -1,
            -1,
            0
    };

    public int[] povs = new int[] {
            -1
    };

    // buttons are pressed or not
    public boolean[] buttons = new boolean[] {
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
    };

    /**
     * TODO: replace this with an enum flag, maybe?
     * @return a bitwise concat of whether buttons are pressed or not
     */
    public short getButtonMask() {
        short output = 0;
        for (int i = 0; i < buttons.length; i++) {
            output += (buttons[i] ? 1 : 0) << i;
        }
        return output;
    }

    public void setButtonMask(short buttonMask) {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = ((buttonMask >> i) & 1) != 0;
        }
    }

    public void setButton(int id, boolean pressed) {
        buttons[id] = pressed;
    }

}
