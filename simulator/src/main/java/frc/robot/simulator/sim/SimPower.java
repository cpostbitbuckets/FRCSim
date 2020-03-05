package frc.robot.simulator.sim;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimPower {
    private static final Logger log = LoggerFactory.getLogger(SimPower.class);

    /**
     * @return Return the battery voltage
     */
    public static double getVinVoltage() {
        return 12;
    }

    public static double getVinCurrent() {
        log.warn("getVinCurrent not implemented yet.");

        return 0;
    }

    public static double getUserVoltage6V() {
        log.warn("getUserVoltage6V not implemented yet.");

        return 0;
    }

    public static double getUserCurrent6V() {
        log.warn("getUserCurrent6V not implemented yet.");

        return 0;
    }

    public static boolean getUserActive6V() {
        log.warn("getUserActive6V not implemented yet.");

        return false;
    }

    public static int getUserCurrentFaults6V() {
        log.warn("getUserCurrentFaults6V not implemented yet.");

        return 0;
    }

    public static double getUserVoltage5V() {
        log.warn("getUserVoltage5V not implemented yet.");

        return 0;
    }

    public static double getUserCurrent5V() {
        log.warn("getUserCurrent5V not implemented yet.");

        return 0;
    }

    public static boolean getUserActive5V() {
        log.warn("getUserActive5V not implemented yet.");

        return false;
    }

    public static int getUserCurrentFaults5V() {
        log.warn("getUserCurrentFaults5V not implemented yet.");

        return 0;
    }

    public static double getUserVoltage3V3() {
        log.warn("getUserVoltage3V3 not implemented yet.");

        return 0;
    }

    public static double getUserCurrent3V3() {
        log.warn("getUserCurrent3V3 not implemented yet.");

        return 0;
    }

    public static boolean getUserActive3V3() {
        log.warn("getUserActive3V3 not implemented yet.");

        return false;
    }

    public static int getUserCurrentFaults3V3() {
        log.warn("getUserCurrentFaults3V3 not implemented yet.");

        return 0;
    }

}
