package frc.robot.simulator.hal;

import edu.wpi.first.hal.JNIWrapper;
import frc.robot.simulator.sim.SimPower;

/**
 * Sim JNI classes must be structured EXACTLY like their counterpart non-sim JNI classes. If a single field or method is added or removed, the ByteBuddy redefine will fail
 */
public class SimPowerJNI extends JNIWrapper {
    public static double getVinVoltage() {
        return SimPower.getVinVoltage();
    }

    public static double getVinCurrent() {
        return SimPower.getVinCurrent();
    }

    public static double getUserVoltage6V() {
        return SimPower.getUserVoltage6V();
    }

    public static double getUserCurrent6V() {
        return SimPower.getUserCurrent6V();
    }

    public static boolean getUserActive6V() {
        return SimPower.getUserActive6V();
    }

    public static int getUserCurrentFaults6V() {
        return SimPower.getUserCurrentFaults6V();
    }

    public static double getUserVoltage5V() {
        return SimPower.getUserVoltage5V();
    }

    public static double getUserCurrent5V() {
        return SimPower.getUserCurrent5V();
    }

    public static boolean getUserActive5V() {
        return SimPower.getUserActive5V();
    }

    public static int getUserCurrentFaults5V() {
        return SimPower.getUserCurrentFaults5V();
    }

    public static double getUserVoltage3V3() {
        return SimPower.getUserVoltage3V3();
    }

    public static double getUserCurrent3V3() {
        return SimPower.getUserCurrent3V3();
    }

    public static boolean getUserActive3V3() {
        return SimPower.getUserActive3V3();
    }

    public static int getUserCurrentFaults3V3() {
        return SimPower.getUserCurrentFaults3V3();
    }
}
