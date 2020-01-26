package frc.robot.simulator.hal.ctre;

/**
 * Sim JNI classes must be structured EXACTLY like their counterpart non-sim JNI classes. If a single field or method is added or removed, the ByteBuddy redefine will fail
 */
public class SimCTREJNIWrapper {
    static boolean libraryLoaded = false;

    static {
    }
}
