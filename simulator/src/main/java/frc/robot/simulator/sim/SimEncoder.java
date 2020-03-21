
package frc.robot.simulator.sim;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimEncoder {
    private static final Logger log = LoggerFactory.getLogger(SimEncoder.class);


    public static int initializeEncoder(int digitalSourceHandleA, int analogTriggerTypeA,
                                        int digitalSourceHandleB, int analogTriggerTypeB,
                                        boolean reverseDirection, int encodingType) {
        log.warn("initializeEncoder not implemented yet.");

        return 0;
    }

    public static void freeEncoder(int encoderHandle) {
        log.warn("freeEncoder not implemented yet.");

    }

    public static void setEncoderSimDevice(int handle, int device) {
        log.warn("setEncoderSimDevice not implemented yet.");

    }

    public static int getEncoder(int encoderHandle) {
        log.warn("getEncoder not implemented yet.");

        return 0;
    }

    public static int getEncoderRaw(int encoderHandle) {
        log.warn("getEncoderRaw not implemented yet.");

        return 0;
    }

    public static int getEncodingScaleFactor(int encoderHandle) {
        log.warn("getEncodingScaleFactor not implemented yet.");

        return 0;
    }

    public static void resetEncoder(int encoderHandle) {
        log.warn("resetEncoder not implemented yet.");

    }

    public static double getEncoderPeriod(int encoderHandle) {
        log.warn("getEncoderPeriod not implemented yet.");

        return 0;
    }

    public static void setEncoderMaxPeriod(int encoderHandle, double maxPeriod) {
        log.warn("setEncoderMaxPeriod not implemented yet.");

    }

    public static boolean getEncoderStopped(int encoderHandle) {
        log.warn("getEncoderStopped not implemented yet.");

        return false;
    }

    public static boolean getEncoderDirection(int encoderHandle) {
        log.warn("getEncoderDirection not implemented yet.");

        return false;
    }

    public static double getEncoderDistance(int encoderHandle) {
        log.warn("getEncoderDistance not implemented yet.");

        return 0;
    }

    public static double getEncoderRate(int encoderHandle) {
        log.warn("getEncoderRate not implemented yet.");

        return 0;
    }

    public static void setEncoderMinRate(int encoderHandle, double minRate) {
        log.warn("setEncoderMinRate not implemented yet.");

    }

    public static void setEncoderDistancePerPulse(int encoderHandle, double distancePerPulse) {
        log.warn("setEncoderDistancePerPulse not implemented yet.");

    }

    public static void setEncoderReverseDirection(int encoderHandle,
                                                  boolean reverseDirection) {
        log.warn("setEncoderReverseDirection not implemented yet.");

    }

    public static void setEncoderSamplesToAverage(int encoderHandle,
                                                  int samplesToAverage) {
        log.warn("setEncoderSamplesToAverage not implemented yet.");

    }

    public static int getEncoderSamplesToAverage(int encoderHandle) {
        log.warn("getEncoderSamplesToAverage not implemented yet.");

        return 0;
    }

    public static void setEncoderIndexSource(int encoderHandle, int digitalSourceHandle,
                                             int analogTriggerType, int indexingType) {
        log.warn("setEncoderIndexSource not implemented yet.");

    }

    public static int getEncoderFPGAIndex(int encoderHandle) {
        log.warn("getEncoderFPGAIndex not implemented yet.");

        return 0;
    }

    public static int getEncoderEncodingScale(int encoderHandle) {
        log.warn("getEncoderEncodingScale not implemented yet.");

        return 0;
    }

    public static double getEncoderDecodingScaleFactor(int encoderHandle) {
        log.warn("getEncoderDecodingScaleFactor not implemented yet.");

        return 0;
    }

    public static double getEncoderDistancePerPulse(int encoderHandle) {
        log.warn("getEncoderDistancePerPulse not implemented yet.");

        return 0;
    }

    public static int getEncoderEncodingType(int encoderHandle) {
        log.warn("getEncoderEncodingType not implemented yet.");

        return 0;
    }

}

