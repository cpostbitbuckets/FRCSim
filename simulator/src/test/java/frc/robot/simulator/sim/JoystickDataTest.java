package frc.robot.simulator.sim;

import org.junit.Test;

import static org.junit.Assert.*;

public class JoystickDataTest {

    @Test
    public void getButtonMask() {
        JoystickData joystickData = new JoystickData();
        joystickData.buttons[0] = true;
        assertEquals(0x01, joystickData.getButtonMask());

        joystickData = new JoystickData();
        joystickData.buttons[2] = true;
        assertEquals(0x04, joystickData.getButtonMask());

        joystickData = new JoystickData();
        joystickData.buttons[0] = true;
        joystickData.buttons[2] = true;
        assertEquals(0x05, joystickData.getButtonMask());

    }

    @Test
    public void setButtonMask() {
        JoystickData joystickData = new JoystickData();
        joystickData.setButtonMask((short) 1);
        assertTrue(joystickData.buttons[0]);
        for (int i = 1; i < joystickData.buttons.length; i++) {
            assertFalse(joystickData.buttons[i]);
        }

        joystickData.setButtonMask((short) 4);
        assertTrue(joystickData.buttons[2]);
        for (int i = 1; i < joystickData.buttons.length; i++) {
            if (i != 2) {
                assertFalse(joystickData.buttons[i]);
            }
        }

        joystickData.setButtonMask((short) 5);
        assertTrue(joystickData.buttons[2]);
        assertTrue(joystickData.buttons[0]);
        for (int i = 1; i < joystickData.buttons.length; i++) {
            if (i != 2 && i != 0) {
                assertFalse(joystickData.buttons[i]);
            }
        }

    }
}