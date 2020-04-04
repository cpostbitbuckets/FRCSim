package frc.robot.simulator.hal;

import edu.wpi.first.hal.JNIWrapper;
import frc.robot.simulator.sim.SimCounter;

import java.nio.IntBuffer;

public class SimCounterJNI extends JNIWrapper {
    public static int initializeCounter(int mode, IntBuffer index) {
        return SimCounter.initializeCounter(mode, index);
    }

    public static void freeCounter(int counterHandle) {
        SimCounter.freeCounter(counterHandle);
    }

    public static void setCounterAverageSize(int counterHandle, int size) {
        SimCounter.setCounterAverageSize(counterHandle, size);
    }

    public static void setCounterUpSource(int counterHandle, int digitalSourceHandle,
                                          int analogTriggerType) {
        SimCounter.setCounterUpSource(counterHandle, digitalSourceHandle, analogTriggerType);
    }

    public static void setCounterUpSourceEdge(int counterHandle, boolean risingEdge,
                                              boolean fallingEdge) {
        SimCounter.setCounterUpSourceEdge(counterHandle, risingEdge, fallingEdge);
    }

    public static void clearCounterUpSource(int counterHandle) {
        SimCounter.clearCounterUpSource(counterHandle);
    }

    public static void setCounterDownSource(int counterHandle, int digitalSourceHandle,
                                            int analogTriggerType) {
        SimCounter.setCounterDownSource(counterHandle, digitalSourceHandle, analogTriggerType);
    }

    public static void setCounterDownSourceEdge(int counterHandle, boolean risingEdge,
                                                boolean fallingEdge) {
        SimCounter.setCounterDownSourceEdge(counterHandle, risingEdge, fallingEdge);
    }

    public static void clearCounterDownSource(int counterHandle) {
        SimCounter.clearCounterDownSource(counterHandle);
    }

    public static void setCounterUpDownMode(int counterHandle) {
        SimCounter.setCounterUpDownMode(counterHandle);
    }

    public static void setCounterExternalDirectionMode(int counterHandle) {
        SimCounter.setCounterExternalDirectionMode(counterHandle);
    }

    public static void setCounterSemiPeriodMode(int counterHandle,
                                                boolean highSemiPeriod) {
        SimCounter.setCounterSemiPeriodMode(counterHandle, highSemiPeriod);
    }

    public static void setCounterPulseLengthMode(int counterHandle, double threshold) {
        SimCounter.setCounterPulseLengthMode(counterHandle, threshold);
    }

    public static int getCounterSamplesToAverage(int counterHandle) {
        return SimCounter.getCounterSamplesToAverage(counterHandle);
    }

    public static void setCounterSamplesToAverage(int counterHandle,
                                                  int samplesToAverage) {
        SimCounter.setCounterSamplesToAverage(counterHandle, samplesToAverage);
    }

    public static void resetCounter(int counterHandle) {
        SimCounter.resetCounter(counterHandle);
    }

    public static int getCounter(int counterHandle) {
        return SimCounter.getCounter(counterHandle);
    }

    public static double getCounterPeriod(int counterHandle) {
        return SimCounter.getCounterPeriod(counterHandle);
    }

    public static void setCounterMaxPeriod(int counterHandle, double maxPeriod) {
        SimCounter.setCounterMaxPeriod(counterHandle, maxPeriod);
    }

    public static void setCounterUpdateWhenEmpty(int counterHandle, boolean enabled) {
        SimCounter.setCounterUpdateWhenEmpty(counterHandle, enabled);
    }

    public static boolean getCounterStopped(int counterHandle) {
        return SimCounter.getCounterStopped(counterHandle);
    }

    public static boolean getCounterDirection(int counterHandle) {
        return SimCounter.getCounterDirection(counterHandle);
    }

    public static void setCounterReverseDirection(int counterHandle,
                                                  boolean reverseDirection) {
        SimCounter.setCounterReverseDirection(counterHandle, reverseDirection);
    }
}
