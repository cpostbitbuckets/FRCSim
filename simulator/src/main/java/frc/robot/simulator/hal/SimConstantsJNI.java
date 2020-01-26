package frc.robot.simulator.hal;

import edu.wpi.first.hal.JNIWrapper;

/**
 * Sim JNI classes must be structured EXACTLY like their counterpart non-sim JNI classes. If a single field or method is added or removed, the ByteBuddy redefine will fail
 */
public class SimConstantsJNI extends JNIWrapper {
    public static int getSystemClockTicksPerMicrosecond() {
        // from ConstantsInternal.h in wpilib
        return 40;
    }
}
