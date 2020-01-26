package frc.robot.simulator.hal;

import edu.wpi.first.hal.JNIWrapper;
import edu.wpi.first.wpiutil.RuntimeLoader;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class only exists to keep ByteBuddy's redefine() method happy. We need a one for one copy of all members and methods of a class
 */
public class SimJNIWrapper {
    static boolean libraryLoaded = false;
    static RuntimeLoader<JNIWrapper> loader = null;

    static {

    }

    /**
     * Force load the library.
     */
    public static synchronized void forceLoad() throws IOException {
    }
}