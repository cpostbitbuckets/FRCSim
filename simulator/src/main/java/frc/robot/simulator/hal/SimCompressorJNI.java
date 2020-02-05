package frc.robot.simulator.hal;

import edu.wpi.first.hal.JNIWrapper;
import frc.robot.simulator.sim.SimCompressor;

public class SimCompressorJNI extends JNIWrapper {
    public static int initializeCompressor(byte module) {
        return SimCompressor.initializeCompressor(module);
    }

    public static boolean checkCompressorModule(byte module) {
        return SimCompressor.checkCompressorModule(module);
    }

    public static boolean getCompressor(int compressorHandle) {
        return SimCompressor.getCompressor(compressorHandle);
    }

    public static void setCompressorClosedLoopControl(int compressorHandle, boolean value) {
        SimCompressor.setCompressorClosedLoopControl(compressorHandle, value);
    }

    public static boolean getCompressorClosedLoopControl(int compressorHandle) {
        return SimCompressor.getCompressorClosedLoopControl(compressorHandle);
    }

    public static boolean getCompressorPressureSwitch(int compressorHandle) {
        return SimCompressor.getCompressorPressureSwitch(compressorHandle);
    }

    public static double getCompressorCurrent(int compressorHandle) {
        return SimCompressor.getCompressorCurrent(compressorHandle);
    }

    public static boolean getCompressorCurrentTooHighFault(int compressorHandle) {
        return SimCompressor.getCompressorCurrentTooHighFault(compressorHandle);
    }

    public static boolean getCompressorCurrentTooHighStickyFault(int compressorHandle) {
        return SimCompressor.getCompressorCurrentTooHighStickyFault(compressorHandle);
    }

    public static boolean getCompressorShortedStickyFault(int compressorHandle) {
        return SimCompressor.getCompressorShortedStickyFault(compressorHandle);
    }

    public static boolean getCompressorShortedFault(int compressorHandle) {
        return SimCompressor.getCompressorShortedFault(compressorHandle);
    }

    public static boolean getCompressorNotConnectedStickyFault(int compressorHandle) {
        return SimCompressor.getCompressorNotConnectedStickyFault(compressorHandle);
    }

    public static boolean getCompressorNotConnectedFault(int compressorHandle) {
        return SimCompressor.getCompressorNotConnectedFault(compressorHandle);
    }

    public static void clearAllPCMStickyFaults(byte compressorModule) {
        SimCompressor.clearAllPCMStickyFaults(compressorModule);
    }
}
