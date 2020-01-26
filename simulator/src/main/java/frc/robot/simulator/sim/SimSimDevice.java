
package frc.robot.simulator.sim;

import edu.wpi.first.hal.HALValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimSimDevice {
    private static final Logger log = LoggerFactory.getLogger(SimSimDevice.class);


    public static int createSimDevice(String name) {
        log.warn("createSimDevice not implemented yet.");

        return 0;
    }

    public static void freeSimDevice(int handle) {
        log.warn("freeSimDevice not implemented yet.");

    }

    public static int createSimValue(int device, String name, boolean readonly,
                                     HALValue initialValue) {
        log.warn("createSimValue not implemented yet.");

        return 0;
    }

    public static int createSimValueDouble(int device, String name, boolean readonly,
                                           double initialValue) {
        log.warn("createSimValueDouble not implemented yet.");

        return 0;
    }

    public static int createSimValueEnum(int device, String name, boolean readonly,
                                         String[] options, int initialValue) {
        log.warn("createSimValueEnum not implemented yet.");

        return 0;
    }

    public static int createSimValueBoolean(int device, String name, boolean readonly,
                                            boolean initialValue) {
        log.warn("createSimValueBoolean not implemented yet.");

        return 0;
    }

    public static HALValue getSimValue(int handle) {
        log.warn("getSimValue not implemented yet.");

        return null;
    }

    public static double getSimValueDouble(int handle) {
        log.warn("getSimValueDouble not implemented yet.");

        return 0;
    }

    public static int getSimValueEnum(int handle) {
        log.warn("getSimValueEnum not implemented yet.");

        return 0;
    }

    public static boolean getSimValueBoolean(int handle) {
        log.warn("getSimValueBoolean not implemented yet.");

        return false;
    }

    public static void setSimValue(int handle, HALValue value) {
        log.warn("setSimValue not implemented yet.");

    }

    public static void setSimValueDouble(int handle, double value) {
        log.warn("setSimValueDouble not implemented yet.");

    }

    public static void setSimValueEnum(int handle, int value) {
        log.warn("setSimValueEnum not implemented yet.");

    }

    public static void setSimValueBoolean(int handle, boolean value) {
        log.warn("setSimValueBoolean not implemented yet.");

    }

}

