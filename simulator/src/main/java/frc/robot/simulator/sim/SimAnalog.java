
package frc.robot.simulator.sim;

import edu.wpi.first.hal.AccumulatorResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimAnalog {
    private static final Logger log = LoggerFactory.getLogger(SimAnalog.class);


    public static int initializeAnalogInputPort(int halPortHandle) {
        log.warn("initializeAnalogInputPort not implemented yet.");

        return 0;
    }

    public static void freeAnalogInputPort(int portHandle) {
        log.warn("freeAnalogInputPort not implemented yet.");

    }

    public static int initializeAnalogOutputPort(int halPortHandle) {
        log.warn("initializeAnalogOutputPort not implemented yet.");

        return 0;
    }

    public static void freeAnalogOutputPort(int portHandle) {
        log.warn("freeAnalogOutputPort not implemented yet.");

    }

    public static boolean checkAnalogModule(byte module) {
        log.warn("checkAnalogModule not implemented yet.");

        return false;
    }

    public static boolean checkAnalogInputChannel(int channel) {
        // always good
        return true;
    }

    public static boolean checkAnalogOutputChannel(int channel) {
        log.warn("checkAnalogOutputChannel not implemented yet.");

        return false;
    }

    public static void setAnalogInputSimDevice(int handle, int device) {
        log.warn("setAnalogInputSimDevice not implemented yet.");

    }

    public static void setAnalogOutput(int portHandle, double voltage) {
        log.warn("setAnalogOutput not implemented yet.");

    }

    public static double getAnalogOutput(int portHandle) {
        log.warn("getAnalogOutput not implemented yet.");

        return 0;
    }

    public static void setAnalogSampleRate(double samplesPerSecond) {
        log.warn("setAnalogSampleRate not implemented yet.");

    }

    public static double getAnalogSampleRate() {
        log.warn("getAnalogSampleRate not implemented yet.");

        return 0;
    }

    public static void setAnalogAverageBits(int analogPortHandle, int bits) {
        log.warn("setAnalogAverageBits not implemented yet.");

    }

    public static int getAnalogAverageBits(int analogPortHandle) {
        log.warn("getAnalogAverageBits not implemented yet.");

        return 0;
    }

    public static void setAnalogOversampleBits(int analogPortHandle, int bits) {
        log.warn("setAnalogOversampleBits not implemented yet.");

    }

    public static int getAnalogOversampleBits(int analogPortHandle) {
        log.warn("getAnalogOversampleBits not implemented yet.");

        return 0;
    }

    public static short getAnalogValue(int analogPortHandle) {
        log.warn("getAnalogValue not implemented yet.");

        return 0;
    }

    public static int getAnalogAverageValue(int analogPortHandle) {
        log.warn("getAnalogAverageValue not implemented yet.");

        return 0;
    }

    public static int getAnalogVoltsToValue(int analogPortHandle, double voltage) {
        log.warn("getAnalogVoltsToValue not implemented yet.");

        return 0;
    }

    public static double getAnalogVoltage(int analogPortHandle) {
        log.warn("getAnalogVoltage not implemented yet.");

        return 0;
    }

    public static double getAnalogAverageVoltage(int analogPortHandle) {
        log.warn("getAnalogAverageVoltage not implemented yet.");

        return 0;
    }

    public static int getAnalogLSBWeight(int analogPortHandle) {
        log.warn("getAnalogLSBWeight not implemented yet.");

        return 0;
    }

    public static int getAnalogOffset(int analogPortHandle) {
        log.warn("getAnalogOffset not implemented yet.");

        return 0;
    }

    public static boolean isAccumulatorChannel(int analogPortHandle) {
        log.warn("isAccumulatorChannel not implemented yet.");

        return false;
    }

    public static void initAccumulator(int analogPortHandle) {
        log.warn("initAccumulator not implemented yet.");

    }

    public static void resetAccumulator(int analogPortHandle) {
        log.warn("resetAccumulator not implemented yet.");

    }

    public static void setAccumulatorCenter(int analogPortHandle, int center) {
        log.warn("setAccumulatorCenter not implemented yet.");

    }

    public static void setAccumulatorDeadband(int analogPortHandle, int deadband) {
        log.warn("setAccumulatorDeadband not implemented yet.");

    }

    public static long getAccumulatorValue(int analogPortHandle) {
        log.warn("getAccumulatorValue not implemented yet.");

        return 0;
    }

    public static int getAccumulatorCount(int analogPortHandle) {
        log.warn("getAccumulatorCount not implemented yet.");

        return 0;
    }

    public static void getAccumulatorOutput(int analogPortHandle, AccumulatorResult result) {
        log.warn("getAccumulatorOutput not implemented yet.");

    }

    public static int initializeAnalogTrigger(int analogInputHandle) {
        log.warn("initializeAnalogTrigger not implemented yet.");

        return 0;
    }

    public static int initializeAnalogTriggerDutyCycle(int dutyCycleHandle) {
        log.warn("initializeAnalogTriggerDutyCycle not implemented yet.");

        return 0;
    }

    public static void cleanAnalogTrigger(int analogTriggerHandle) {
        log.warn("cleanAnalogTrigger not implemented yet.");

    }

    public static void setAnalogTriggerLimitsRaw(int analogTriggerHandle, int lower,
                                                 int upper) {
        log.warn("setAnalogTriggerLimitsRaw not implemented yet.");

    }

    public static void setAnalogTriggerLimitsDutyCycle(int analogTriggerHandle, double lower,
                                                       double higher) {
        log.warn("setAnalogTriggerLimitsDutyCycle not implemented yet.");

    }

    public static void setAnalogTriggerLimitsVoltage(int analogTriggerHandle,
                                                     double lower, double upper) {
        log.warn("setAnalogTriggerLimitsVoltage not implemented yet.");

    }

    public static void setAnalogTriggerAveraged(int analogTriggerHandle,
                                                boolean useAveragedValue) {
        log.warn("setAnalogTriggerAveraged not implemented yet.");

    }

    public static void setAnalogTriggerFiltered(int analogTriggerHandle,
                                                boolean useFilteredValue) {
        log.warn("setAnalogTriggerFiltered not implemented yet.");

    }

    public static boolean getAnalogTriggerInWindow(int analogTriggerHandle) {
        log.warn("getAnalogTriggerInWindow not implemented yet.");

        return false;
    }

    public static boolean getAnalogTriggerTriggerState(int analogTriggerHandle) {
        log.warn("getAnalogTriggerTriggerState not implemented yet.");

        return false;
    }

    public static boolean getAnalogTriggerOutput(int analogTriggerHandle, int type) {
        log.warn("getAnalogTriggerOutput not implemented yet.");

        return false;
    }

    public static int getAnalogTriggerFPGAIndex(int analogTriggerHandle) {
        log.warn("getAnalogTriggerFPGAIndex not implemented yet.");

        return 0;
    }

}

