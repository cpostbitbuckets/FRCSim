package frc.robot.simulator.sim;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class SimSolenoid {
    private static final Logger log = LoggerFactory.getLogger(SimSolenoid.class);

    private static int nextHandle = 10000;

    public static class SimSolenoidPort {
        public int handle;
        public int module;
        public int channel;
        public boolean state;

        public SimSolenoidPort(int module, int channel) {
            this.module = module;
            this.channel = channel;
            this.handle = nextHandle++;
        }
    }

    private static Map<Integer, SimSolenoidPort> solenoidPortsByHandle = new HashMap<>();

    /**
     * Create a new Solenoid port, returning the handle
     * @param module
     * @param channel
     * @return
     */
    public static int createSolenoidPort(int module, int channel) {
        SimSolenoidPort simSolenoidPort = new SimSolenoidPort(module, channel);
        solenoidPortsByHandle.put(simSolenoidPort.handle, simSolenoidPort);
        return simSolenoidPort.handle;
    }

    public static int initializeSolenoidPort(int halPortHandle) {
        SimSolenoidPort solenoidPort = solenoidPortsByHandle.get(halPortHandle);
        if (solenoidPort != null) {
            return solenoidPort.handle;
        } else {
            throw new IllegalArgumentException("Attempted to initialzie a solenoid port with an invalid handle");
        }
    }

    /**
     * This is called to check that a solenoid at the module number is available
     * so we should create one in our sim.
     * @param module
     * @return true because we always have unlimited hardware in sim mode!
     */
    public static boolean checkSolenoidModule(int module) {
        return true;
    }

    /**
     * This is called to check that a solenoid channel is available
     * @param channel
     * @return true because we always have unlimited hardware in sim mode!
     */
    public static boolean checkSolenoidChannel(int channel) {
        return true;
    }

    public static void freeSolenoidPort(int portHandle) {
        log.warn("freeSolenoidPort not implemented yet.");

    }

    public static void setSolenoid(int portHandle, boolean on) {
        SimSolenoidPort solenoidPort = solenoidPortsByHandle.get(portHandle);
        solenoidPort.state = on;
    }

    public static boolean getSolenoid(int portHandle) {
        SimSolenoidPort solenoidPort = solenoidPortsByHandle.get(portHandle);
        return solenoidPort.state;
    }

    public static int getAllSolenoids(int module) {
        log.warn("getAllSolenoids not implemented yet.");

        return 0;
    }

    public static int getPCMSolenoidBlackList(int module) {
        log.warn("getPCMSolenoidBlackList not implemented yet.");

        return 0;
    }

    public static boolean getPCMSolenoidVoltageStickyFault(int module) {
        log.warn("getPCMSolenoidVoltageStickyFault not implemented yet.");

        return false;
    }

    public static boolean getPCMSolenoidVoltageFault(int module) {
        log.warn("getPCMSolenoidVoltageFault not implemented yet.");

        return false;
    }

    public static void clearAllPCMStickyFaults(int module) {
        log.warn("clearAllPCMStickyFaults not implemented yet.");

    }

    public static void setOneShotDuration(int portHandle, long durationMS) {
        log.warn("setOneShotDuration not implemented yet.");

    }

    public static void fireOneShot(int portHandle) {
        log.warn("fireOneShot not implemented yet.");

    }

}

