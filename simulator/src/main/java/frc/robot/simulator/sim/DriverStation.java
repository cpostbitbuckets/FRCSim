package frc.robot.simulator.sim;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DriverStation {
    private static final Logger log = LoggerFactory.getLogger(DriverStation.class);
    private boolean enabled = false;
    private boolean autonomous = false;
    private boolean test = false;
    private boolean emergencyStop = false;
    private boolean fmsAttached = false;  // field management system
    private boolean dsAttached = false;
    private long lastTimeEnabled = 0;

    public void enable() {
        log.info("DriverStation enabled");
        enabled = true;
        dsAttached =  true;
        lastTimeEnabled = System.currentTimeMillis();
    }

    public void setEnabled(boolean enabled) {
        if (this.enabled == false && enabled == true) {
            log.info("DriverStation enabled");
            lastTimeEnabled = System.currentTimeMillis();
        } else if (this.enabled = true && enabled == false) {
            log.info("DriverStation disabled");
            lastTimeEnabled = 0;
        }
        this.enabled = enabled;
    }

    public void setAutonomous(boolean autonomous) {
        this.autonomous = autonomous;
    }

    public void setTest(boolean test) {
        this.test = test;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isAutonomous() {
        return autonomous;
    }

    public boolean isTest() {
        return test;
    }

    /**
     * @return a bitwise representation of our state
     */
    public int getControlWord() {
        int result = 0;
        result |= (enabled ? 1 : 0);
        result |= (autonomous ? 1 : 0) << 1;
        result |= (test ? 1 : 0) << 2;
        result |= (emergencyStop ? 1 : 0) << 3;
        result |= (fmsAttached ? 1 : 0) << 4;
        result |= (dsAttached ? 1 : 0) << 5;

        return result;
    }

    public double getLastTimeEnabled() {
        if (lastTimeEnabled == 0) {
            return 0;
        }
        return (System.currentTimeMillis() - lastTimeEnabled) / 1000.0;
    }
}
