package frc.robot.simulator.sim;

import edu.wpi.first.hal.HALUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This simulate the NotifierJNI. It's basically just a thread launcher that blocks until some delay has passed
 * The robot calls into the notifier to get notified every 20 ms and run its loops
 */
public class SimNotifier {
    private static final Logger log = LoggerFactory.getLogger(SimNotifier.class);

    private static Long[] notifiers = new Long[10];
    private static int notifierIndex = 0;

    /**
     * Initializes the notifier.
     */
    public static int initializeNotifier() {
        notifiers[notifierIndex] = HALUtil.getFPGATime();
        return notifierIndex++;
    }

    public static void setNotifierName(int notifierHandle, String name) {
        log.warn("setNotifierName not implemented yet.");
    }

    /**
     * Wakes up the waiter with time=0.  Note: after this function is called, all
     * calls to waitForNotifierAlarm() will immediately start returning 0.
     */
    public static void stopNotifier(int notifierHandle) {
        if (notifierHandle > 0 && notifierHandle <= notifierIndex && notifiers[notifierHandle] != null) {
            // set the time to 0
            notifiers[notifierHandle] = Long.valueOf(0);
        }
    }

    /**
     * Deletes the notifier object when we are done with it.
     */
    public static void cleanNotifier(int notifierHandle) {
        if (notifierHandle > 0 && notifierHandle <= notifierIndex && notifiers[notifierHandle] != null) {
            // set the time to 0
            notifiers[notifierHandle] = null;
        }
    }

    /***
     *
     * @param notifierHandle the index in the notifiers array to use
     * @param triggerTime This is the next time we want this alarm to trigger, in microseconds
     */
    public static void updateNotifierAlarm(int notifierHandle, long triggerTime) {
        if (notifierHandle >= 0 && notifierHandle < notifierIndex && notifiers[notifierHandle] != null) {
            notifiers[notifierHandle] = Long.valueOf(triggerTime);
        }
    }

    /**
     * Cancels any pending wakeups set by updateNotifierAlarm().  Does NOT wake
     * up any waiters.
     */
    public static void cancelNotifierAlarm(int notifierHandle) {
        if (notifierHandle > 0 && notifierHandle <= notifierIndex && notifiers[notifierHandle] != null) {
            notifiers[notifierHandle] = null;
        }
    }

    /**
     * Block until woken up by an alarm (or stop).
     * @return Time when woken up (0 to exit)
     */
    public static long waitForNotifierAlarm(int notifierHandle) {
        if (notifierHandle >= 0 && notifierHandle < notifierIndex && notifiers[notifierHandle] != null) {
            long triggerTime = notifiers[notifierHandle];
            long nanosToWait = (triggerTime - HALUtil.getFPGATime()) * 1000;
            long waitUntil = System.nanoTime() + nanosToWait;
            try {
                if (nanosToWait - 1000000 > 0) {
                    // free up the processor for our wait time -1ms
                    Thread.sleep((nanosToWait - 1000000) / 10000000);
                }
            } catch (InterruptedException e) {
                log.error("Failed to sleep in waitForNotifierAlarm", e);
            }
            while(waitUntil > System.nanoTime()) {
                ;
            }

        }
        return HALUtil.getFPGATime();
    }

}
