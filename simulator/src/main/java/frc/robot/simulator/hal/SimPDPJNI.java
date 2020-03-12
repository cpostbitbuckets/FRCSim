package frc.robot.simulator.hal;

import edu.wpi.first.hal.JNIWrapper;
import frc.robot.simulator.sim.SimPDP;

public class SimPDPJNI extends JNIWrapper {
    public static int initializePDP(int module) {
        return SimPDP.initializePDP(module);
    }

    public static boolean checkPDPModule(int module) {
        return SimPDP.checkPDPModule(module);
    }

    public static boolean checkPDPChannel(int channel) {
        return SimPDP.checkPDPChannel(channel);
    }

    public static double getPDPTemperature(int handle) {
        return SimPDP.getPDPTemperature(handle);
    }

    public static double getPDPVoltage(int handle) {
        return SimPDP.getPDPVoltage(handle);
    }

    public static double getPDPChannelCurrent(byte channel, int handle) {
        return SimPDP.getPDPChannelCurrent(channel, handle);
    }

    public static void getPDPAllCurrents(int handle, double[] currents) {
        SimPDP.getPDPAllCurrents(handle, currents);
    }

    public static double getPDPTotalCurrent(int handle) {
        return SimPDP.getPDPTotalCurrent(handle);
    }

    public static double getPDPTotalPower(int handle) {
        return SimPDP.getPDPTotalPower(handle);
    }

    public static double getPDPTotalEnergy(int handle) {
        return SimPDP.getPDPTotalEnergy(handle);
    }

    public static void resetPDPTotalEnergy(int handle) {
        SimPDP.resetPDPTotalEnergy(handle);
    }

    public static void clearPDPStickyFaults(int handle) {
        SimPDP.clearPDPStickyFaults(handle);
    }
}
