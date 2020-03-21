package frc.robot.simulator.sim;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimPDP {
    private static final Logger log = LoggerFactory.getLogger(SimPDP.class);


    public static int initializePDP(int module) {
        log.warn("initializePDP not implemented yet.");

        return 0;
    }

    public static boolean checkPDPModule(int module) {
        // our PDP is always ready! UNLIMITED POWER!
        return true;
    }

    public static boolean checkPDPChannel(int channel) {
        // sure, it's good. probably.
        return true;
    }

    public static double getPDPTemperature(int handle) {
        log.warn("getPDPTemperature not implemented yet.");

        return 0;
    }

    public static double getPDPVoltage(int handle) {
        log.warn("getPDPVoltage not implemented yet.");

        return 0;
    }

    public static double getPDPChannelCurrent(byte channel, int handle) {
        log.warn("getPDPChannelCurrent not implemented yet.");

        return 0;
    }

    public static void getPDPAllCurrents(int handle, double[] currents) {
        log.warn("getPDPAllCurrents not implemented yet.");

    }

    public static double getPDPTotalCurrent(int handle) {
        log.warn("getPDPTotalCurrent not implemented yet.");

        return 0;
    }

    public static double getPDPTotalPower(int handle) {
        log.warn("getPDPTotalPower not implemented yet.");

        return 0;
    }

    public static double getPDPTotalEnergy(int handle) {
        log.warn("getPDPTotalEnergy not implemented yet.");

        return 0;
    }

    public static void resetPDPTotalEnergy(int handle) {
        log.warn("resetPDPTotalEnergy not implemented yet.");

    }

    public static void clearPDPStickyFaults(int handle) {
        log.warn("clearPDPStickyFaults not implemented yet.");

    }

}
