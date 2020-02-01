package frc.robot.simulator.sim;

import com.google.inject.Inject;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.simulator.sim.events.EventManager;
import frc.robot.simulator.sim.solenoids.SimSolenoidPort;
import frc.robot.simulator.sim.solenoids.SolenoidStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimSolenoid {
    private static final Logger log = LoggerFactory.getLogger(SimSolenoid.class);

    @Inject
    static SolenoidStore solenoidStore;

    public static int initializeSolenoidPort(int halPortHandle) {
        SimSolenoidPort solenoidPort = solenoidStore.get(halPortHandle);
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
        SimSolenoidPort solenoid = solenoidStore.get(portHandle);
        solenoid.state = on ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse;
        EventManager.publish(solenoid);
    }

    public static boolean getSolenoid(int portHandle) {
        SimSolenoidPort solenoidPort = solenoidStore.get(portHandle);
        return solenoidPort.state == DoubleSolenoid.Value.kForward;
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

