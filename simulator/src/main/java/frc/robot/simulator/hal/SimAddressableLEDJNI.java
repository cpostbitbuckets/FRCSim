package frc.robot.simulator.hal;

import edu.wpi.first.hal.JNIWrapper;
import frc.robot.simulator.sim.SimAddressableLED;

public class SimAddressableLEDJNI extends JNIWrapper {
    public static int initialize(int pwmHandle) {
        return SimAddressableLED.initialize(pwmHandle);
    }
    public static void free(int handle) {
        SimAddressableLED.free(handle);
    }

    public static void setLength(int handle, int length) {
        SimAddressableLED.setLength(handle, length);
    }
    public static void setData(int handle, byte[] data) {
        SimAddressableLED.setData(handle, data);
    }

    public static void setBitTiming(int handle, int lowTime0, int highTime0, int lowTime1, int highTime1) {
        SimAddressableLED.setBitTiming(handle, lowTime0, highTime0, lowTime1, highTime1);
    }
    public static void setSyncTime(int handle, int syncTime) {
        SimAddressableLED.setSyncTime(handle, syncTime);
    }

    public static void start(int handle) {
        SimAddressableLED.start(handle);
    }
    public static void stop(int handle) {
        SimAddressableLED.stop(handle);
    }
}

