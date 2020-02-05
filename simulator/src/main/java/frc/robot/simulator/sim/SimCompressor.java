package frc.robot.simulator.sim;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimCompressor {
    private static final Logger log = LoggerFactory.getLogger(SimCompressor.class);


    public static int initializeCompressor(byte module) {
        log.warn("initializeCompressor not implemented yet.");

        return 0;
    }

    public static boolean checkCompressorModule(byte module) {
        log.warn("checkCompressorModule not implemented yet.");

        return false;
    }

    public static boolean getCompressor(int compressorHandle) {
        log.warn("getCompressor not implemented yet.");

        return false;
    }

    public static void setCompressorClosedLoopControl(int compressorHandle, boolean value) {
        log.warn("setCompressorClosedLoopControl not implemented yet.");

    }

    public static boolean getCompressorClosedLoopControl(int compressorHandle) {
        log.warn("getCompressorClosedLoopControl not implemented yet.");

        return false;
    }

    public static boolean getCompressorPressureSwitch(int compressorHandle) {
        log.warn("getCompressorPressureSwitch not implemented yet.");

        return false;
    }

    public static double getCompressorCurrent(int compressorHandle) {
        log.warn("getCompressorCurrent not implemented yet.");

        return 0;
    }

    public static boolean getCompressorCurrentTooHighFault(int compressorHandle) {
        log.warn("getCompressorCurrentTooHighFault not implemented yet.");

        return false;
    }

    public static boolean getCompressorCurrentTooHighStickyFault(int compressorHandle) {
        log.warn("getCompressorCurrentTooHighStickyFault not implemented yet.");

        return false;
    }

    public static boolean getCompressorShortedStickyFault(int compressorHandle) {
        log.warn("getCompressorShortedStickyFault not implemented yet.");

        return false;
    }

    public static boolean getCompressorShortedFault(int compressorHandle) {
        log.warn("getCompressorShortedFault not implemented yet.");

        return false;
    }

    public static boolean getCompressorNotConnectedStickyFault(int compressorHandle) {
        log.warn("getCompressorNotConnectedStickyFault not implemented yet.");

        return false;
    }

    public static boolean getCompressorNotConnectedFault(int compressorHandle) {
        log.warn("getCompressorNotConnectedFault not implemented yet.");

        return false;
    }

    public static void clearAllPCMStickyFaults(byte compressorModule) {
        log.warn("clearAllPCMStickyFaults not implemented yet.");

    }

}
