package frc.robot.simulator.hal;

import edu.wpi.first.hal.JNIWrapper;
import frc.robot.simulator.sim.SimNotifier;

/**
 * The NotifierJNI class directly wraps the C++ HAL NotifierManager.
 *
 * <p>This class is not meant for direct use by teams. Instead, the edu.wpi.first.wpilibj.NotifierManager
 * class, which corresponds to the C++ NotifierManager class, should be used.
 */
public class SimNotifierJNI extends JNIWrapper {
    /**
     * Initializes the notifier.
     */
    public static int initializeNotifier() {
        return SimNotifier.initializeNotifier();
    }

    /**
     * Sets the name of the notifier.
     */
    public static void setNotifierName(int notifierHandle, String name) {
        SimNotifier.setNotifierName(notifierHandle, name);
    }

    /**
     * Wakes up the waiter with time=0.  Note: after this function is called, all
     * calls to waitForNotifierAlarm() will immediately start returning 0.
     */
    public static void stopNotifier(int notifierHandle) {
        SimNotifier.stopNotifier(notifierHandle);
    }

    /**
     * Deletes the notifier object when we are done with it.
     */
    public static void cleanNotifier(int notifierHandle) {
        SimNotifier.cleanNotifier(notifierHandle);
    }

    /**
     * Sets the notifier to wakeup the waiter in another triggerTime microseconds.
     */
    public static void updateNotifierAlarm(int notifierHandle, long triggerTime) {
        SimNotifier.updateNotifierAlarm(notifierHandle, triggerTime);
    }

    /**
     * Cancels any pending wakeups set by updateNotifierAlarm().  Does NOT wake
     * up any waiters.
     */
    public static void cancelNotifierAlarm(int notifierHandle) {
        SimNotifier.cancelNotifierAlarm(notifierHandle);
    }

    /**
     * Block until woken up by an alarm (or stop).
     * @return Time when woken up.
     */
    public static long waitForNotifierAlarm(int notifierHandle) {
        return SimNotifier.waitForNotifierAlarm(notifierHandle);
    }
}

