package frc.robot.simulator.hal;

import edu.wpi.first.hal.JNIWrapper;
import frc.robot.simulator.sim.SimEncoder;

public class SimEncoderJNI extends JNIWrapper {
    public static int initializeEncoder(int digitalSourceHandleA, int analogTriggerTypeA,
                                        int digitalSourceHandleB, int analogTriggerTypeB,
                                        boolean reverseDirection, int encodingType) {
        return SimEncoder.initializeEncoder(digitalSourceHandleA, analogTriggerTypeA, digitalSourceHandleB, analogTriggerTypeB, reverseDirection, encodingType);
    }

    public static void freeEncoder(int encoderHandle) {
        SimEncoder.freeEncoder(encoderHandle);
    }

    public static void setEncoderSimDevice(int handle, int device) {
        SimEncoder.setEncoderSimDevice(handle, device);
    }

    public static int getEncoder(int encoderHandle) {
        return SimEncoder.getEncoder(encoderHandle);
    }

    public static int getEncoderRaw(int encoderHandle) {
        return SimEncoder.getEncoderRaw(encoderHandle);
    }

    public static int getEncodingScaleFactor(int encoderHandle) {
        return SimEncoder.getEncodingScaleFactor(encoderHandle);
    }

    public static void resetEncoder(int encoderHandle) {
        SimEncoder.resetEncoder(encoderHandle);
    }

    public static double getEncoderPeriod(int encoderHandle) {
        return SimEncoder.getEncoderPeriod(encoderHandle);
    }

    public static void setEncoderMaxPeriod(int encoderHandle, double maxPeriod) {
        SimEncoder.setEncoderMaxPeriod(encoderHandle, maxPeriod);
    }

    public static boolean getEncoderStopped(int encoderHandle) {
        return SimEncoder.getEncoderStopped(encoderHandle);
    }

    public static boolean getEncoderDirection(int encoderHandle) {
        return SimEncoder.getEncoderDirection(encoderHandle);
    }

    public static double getEncoderDistance(int encoderHandle) {
        return SimEncoder.getEncoderDistance(encoderHandle);
    }

    public static double getEncoderRate(int encoderHandle) {
        return SimEncoder.getEncoderRate(encoderHandle);
    }

    public static void setEncoderMinRate(int encoderHandle, double minRate) {
        SimEncoder.setEncoderMinRate(encoderHandle, minRate);
    }

    public static void setEncoderDistancePerPulse(int encoderHandle, double distancePerPulse) {
        SimEncoder.setEncoderDistancePerPulse(encoderHandle, distancePerPulse);
    }

    public static void setEncoderReverseDirection(int encoderHandle,
                                                  boolean reverseDirection) {
        SimEncoder.setEncoderReverseDirection(encoderHandle, reverseDirection);
    }

    public static void setEncoderSamplesToAverage(int encoderHandle,
                                                  int samplesToAverage) {
        SimEncoder.setEncoderSamplesToAverage(encoderHandle, samplesToAverage);
    }

    public static int getEncoderSamplesToAverage(int encoderHandle) {
        return SimEncoder.getEncoderSamplesToAverage(encoderHandle);
    }

    public static void setEncoderIndexSource(int encoderHandle, int digitalSourceHandle,
                                             int analogTriggerType, int indexingType) {
        SimEncoder.setEncoderIndexSource(encoderHandle, digitalSourceHandle, analogTriggerType, indexingType);
    }

    @SuppressWarnings("AbbreviationAsWordInName")
    public static int getEncoderFPGAIndex(int encoderHandle) {
        return SimEncoder.getEncoderFPGAIndex(encoderHandle);
    }

    public static int getEncoderEncodingScale(int encoderHandle) {
        return SimEncoder.getEncoderEncodingScale(encoderHandle);
    }

    public static double getEncoderDecodingScaleFactor(int encoderHandle) {
        return SimEncoder.getEncoderDecodingScaleFactor(encoderHandle);
    }

    public static double getEncoderDistancePerPulse(int encoderHandle) {
        return SimEncoder.getEncoderDistancePerPulse(encoderHandle);
    }

    public static int getEncoderEncodingType(int encoderHandle) {
        return SimEncoder.getEncoderEncodingType(encoderHandle);
    }
}

