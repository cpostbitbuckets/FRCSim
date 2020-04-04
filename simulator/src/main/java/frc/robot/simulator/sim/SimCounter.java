
package frc.robot.simulator.sim;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.IntBuffer;

public class SimCounter {
    private static final Logger log = LoggerFactory.getLogger(SimCounter.class);


    public static int initializeCounter(int mode, IntBuffer index) {
        log.warn("initializeCounter not implemented yet.");

        return 0;
    }

    public static void freeCounter(int counterHandle) {
        log.warn("freeCounter not implemented yet.");

    }

    public static void setCounterAverageSize(int counterHandle, int size) {
        log.warn("setCounterAverageSize not implemented yet.");

    }

    public static void setCounterUpSource(int counterHandle, int digitalSourceHandle,
                                          int analogTriggerType) {
        log.warn("setCounterUpSource not implemented yet.");

    }

    public static void setCounterUpSourceEdge(int counterHandle, boolean risingEdge,
                                              boolean fallingEdge) {
        log.warn("setCounterUpSourceEdge not implemented yet.");

    }

    public static void clearCounterUpSource(int counterHandle) {
        log.warn("clearCounterUpSource not implemented yet.");

    }

    public static void setCounterDownSource(int counterHandle, int digitalSourceHandle,
                                            int analogTriggerType) {
        log.warn("setCounterDownSource not implemented yet.");

    }

    public static void setCounterDownSourceEdge(int counterHandle, boolean risingEdge,
                                                boolean fallingEdge) {
        log.warn("setCounterDownSourceEdge not implemented yet.");

    }

    public static void clearCounterDownSource(int counterHandle) {
        log.warn("clearCounterDownSource not implemented yet.");

    }

    public static void setCounterUpDownMode(int counterHandle) {
        log.warn("setCounterUpDownMode not implemented yet.");

    }

    public static void setCounterExternalDirectionMode(int counterHandle) {
        log.warn("setCounterExternalDirectionMode not implemented yet.");

    }

    public static void setCounterSemiPeriodMode(int counterHandle,
                                                boolean highSemiPeriod) {
        log.warn("setCounterSemiPeriodMode not implemented yet.");

    }

    public static void setCounterPulseLengthMode(int counterHandle, double threshold) {
        log.warn("setCounterPulseLengthMode not implemented yet.");

    }

    public static int getCounterSamplesToAverage(int counterHandle) {
        log.warn("getCounterSamplesToAverage not implemented yet.");

        return 0;
    }

    public static void setCounterSamplesToAverage(int counterHandle,
                                                  int samplesToAverage) {
        log.warn("setCounterSamplesToAverage not implemented yet.");

    }

    public static void resetCounter(int counterHandle) {
        log.warn("resetCounter not implemented yet.");

    }

    public static int getCounter(int counterHandle) {
        log.warn("getCounter not implemented yet.");

        return 0;
    }

    public static double getCounterPeriod(int counterHandle) {
        log.warn("getCounterPeriod not implemented yet.");

        return 0;
    }

    public static void setCounterMaxPeriod(int counterHandle, double maxPeriod) {
        log.warn("setCounterMaxPeriod not implemented yet.");

    }

    public static void setCounterUpdateWhenEmpty(int counterHandle, boolean enabled) {
        log.warn("setCounterUpdateWhenEmpty not implemented yet.");

    }

    public static boolean getCounterStopped(int counterHandle) {
        log.warn("getCounterStopped not implemented yet.");

        return false;
    }

    public static boolean getCounterDirection(int counterHandle) {
        log.warn("getCounterDirection not implemented yet.");

        return false;
    }

    public static void setCounterReverseDirection(int counterHandle,
                                                  boolean reverseDirection) {
        log.warn("setCounterReverseDirection not implemented yet.");

    }

}

