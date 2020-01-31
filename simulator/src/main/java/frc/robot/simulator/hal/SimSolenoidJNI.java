package frc.robot.simulator.hal;

import edu.wpi.first.hal.JNIWrapper;
import frc.robot.simulator.sim.SimSolenoid;

public class SimSolenoidJNI extends JNIWrapper {
    public static int initializeSolenoidPort(int halPortHandle) {
        return SimSolenoid.initializeSolenoidPort(halPortHandle);
    }

    public static boolean checkSolenoidModule(int module) {
        return SimSolenoid.checkSolenoidModule(module);
    }

    public static boolean checkSolenoidChannel(int channel) {
        return SimSolenoid.checkSolenoidChannel(channel);
    }

    public static void freeSolenoidPort(int portHandle) {
        SimSolenoid.freeSolenoidPort(portHandle);
    }

    public static void setSolenoid(int portHandle, boolean on) {
        SimSolenoid.setSolenoid(portHandle, on);
    }

    public static boolean getSolenoid(int portHandle) {
        return SimSolenoid.getSolenoid(portHandle);
    }

    public static int getAllSolenoids(int module) {
        return SimSolenoid.getAllSolenoids(module);
    }

    public static int getPCMSolenoidBlackList(int module) {
        return SimSolenoid.getPCMSolenoidBlackList(module);
    }

    public static boolean getPCMSolenoidVoltageStickyFault(int module) {
        return SimSolenoid.getPCMSolenoidVoltageStickyFault(module);
    }

    public static boolean getPCMSolenoidVoltageFault(int module) {
        return SimSolenoid.getPCMSolenoidVoltageFault(module);
    }

    public static void clearAllPCMStickyFaults(int module) {
        SimSolenoid.clearAllPCMStickyFaults(module);
    }

    public static void setOneShotDuration(int portHandle, long durationMS) {
        SimSolenoid.setOneShotDuration(portHandle, durationMS);
    }

    public static void fireOneShot(int portHandle) {
        SimSolenoid.fireOneShot(portHandle);
    }

}
