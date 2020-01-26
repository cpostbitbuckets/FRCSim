
package frc.robot.simulator.sim;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimDIO {
    private static final Logger log = LoggerFactory.getLogger(SimDIO.class);

    private static Map<Integer, Boolean> dioByHandle = new ConcurrentHashMap<>();

    public static int initializeDIOPort(int halPortHandle, boolean input) {
        log.warn("initializeDIOPort not implemented yet.");

        return 0;
    }

    public static boolean checkDIOChannel(int channel) {
        log.warn("checkDIOChannel not implemented yet.");

        return true;
    }

    public static void freeDIOPort(int dioPortHandle) {
        log.warn("freeDIOPort not implemented yet.");

    }

    public static void setDIOSimDevice(int handle, int device) {
        log.warn("setDIOSimDevice not implemented yet.");
    }

    public static void setDIO(int dioPortHandle, short value) {
        dioByHandle.put(dioPortHandle, value > 0);
    }

    public static void setDIODirection(int dioPortHandle, boolean input) {
        log.warn("setDIODirection not implemented yet.");

    }

    public static boolean getDIO(int dioPortHandle) {
        return dioByHandle.getOrDefault(dioPortHandle, false);
    }

    public static boolean getDIODirection(int dioPortHandle) {
        log.warn("getDIODirection not implemented yet.");

        return false;
    }

    public static void pulse(int dioPortHandle, double pulseLength) {
        log.warn("pulse not implemented yet.");

    }

    public static boolean isPulsing(int dioPortHandle) {
        log.warn("isPulsing not implemented yet.");

        return false;
    }

    public static boolean isAnyPulsing() {
        log.warn("isAnyPulsing not implemented yet.");

        return false;
    }

    public static short getLoopTiming() {
        log.warn("getLoopTiming not implemented yet.");

        return 0;
    }

    public static int allocateDigitalPWM() {
        log.warn("allocateDigitalPWM not implemented yet.");

        return 0;
    }

    public static void freeDigitalPWM(int pwmGenerator) {
        log.warn("freeDigitalPWM not implemented yet.");

    }

    public static void setDigitalPWMRate(double rate) {
        log.warn("setDigitalPWMRate not implemented yet.");

    }

    public static void setDigitalPWMDutyCycle(int pwmGenerator, double dutyCycle) {
        log.warn("setDigitalPWMDutyCycle not implemented yet.");

    }

    public static void setDigitalPWMOutputChannel(int pwmGenerator, int channel) {
        log.warn("setDigitalPWMOutputChannel not implemented yet.");

    }

}

