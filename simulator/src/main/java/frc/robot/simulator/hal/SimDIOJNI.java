package frc.robot.simulator.hal;

import edu.wpi.first.hal.JNIWrapper;
import frc.robot.simulator.sim.SimDIO;

/**
 * Sim JNI classes must be structured EXACTLY like their counterpart non-sim JNI classes. If a single field or method is added or removed, the ByteBuddy redefine will fail
 */
public class SimDIOJNI extends JNIWrapper {
    public static int initializeDIOPort(int halPortHandle, boolean input) {
        return SimDIO.initializeDIOPort(halPortHandle, input);
    }

    public static boolean checkDIOChannel(int channel) {
        return SimDIO.checkDIOChannel(channel);
    }

    public static void freeDIOPort(int dioPortHandle) {
        SimDIO.freeDIOPort(dioPortHandle);
    }

    public static void setDIOSimDevice(int handle, int device) {
        SimDIO.setDIOSimDevice(handle, device);
    }

    // TODO(Thad): Switch this to use boolean
    public static void setDIO(int dioPortHandle, short value) {
        SimDIO.setDIO(dioPortHandle, value);
    }

    public static void setDIODirection(int dioPortHandle, boolean input) {
        SimDIO.setDIODirection(dioPortHandle, input);
    }

    public static boolean getDIO(int dioPortHandle) {
        return SimDIO.getDIO(dioPortHandle);
    }

    public static boolean getDIODirection(int dioPortHandle) {
        return SimDIO.getDIODirection(dioPortHandle);
    }

    public static void pulse(int dioPortHandle, double pulseLength) {
        SimDIO.pulse(dioPortHandle, pulseLength);
    }

    public static boolean isPulsing(int dioPortHandle) {
        return SimDIO.isPulsing(dioPortHandle);
    }

    public static boolean isAnyPulsing() {
        return SimDIO.isAnyPulsing();
    }

    public static short getLoopTiming() {
        return SimDIO.getLoopTiming();
    }

    public static int allocateDigitalPWM() {
        return SimDIO.allocateDigitalPWM();
    }

    public static void freeDigitalPWM(int pwmGenerator) {
        SimDIO.freeDigitalPWM(pwmGenerator);
    }

    public static void setDigitalPWMRate(double rate) {
        SimDIO.setDigitalPWMRate(rate);
    }

    public static void setDigitalPWMDutyCycle(int pwmGenerator, double dutyCycle) {
        SimDIO.setDigitalPWMDutyCycle(pwmGenerator, dutyCycle);
    }

    public static void setDigitalPWMOutputChannel(int pwmGenerator, int channel) {
        SimDIO.setDigitalPWMOutputChannel(pwmGenerator, channel);
    }
}
