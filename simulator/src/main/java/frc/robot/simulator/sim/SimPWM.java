
package frc.robot.simulator.sim;

import edu.wpi.first.hal.PWMConfigDataResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimPWM {
    private static final Logger log = LoggerFactory.getLogger(SimPWM.class);

    public static int initializePWMPort(int halPortHandle) {
        log.warn("initializePWMPort not implemented yet.");

        return 0;
    }

    public static boolean checkPWMChannel(int channel) {
        // TODO: Do somthing other than return true?
        return true;
    }

    public static void freePWMPort(int pwmPortHandle) {
        log.warn("freePWMPort not implemented yet.");

    }

    public static void setPWMConfigRaw(int pwmPortHandle, int maxPwm,
                                       int deadbandMaxPwm, int centerPwm,
                                       int deadbandMinPwm, int minPwm) {
        log.warn("setPWMConfigRaw not implemented yet.");

    }

    public static void setPWMConfig(int pwmPortHandle, double maxPwm,
                                    double deadbandMaxPwm, double centerPwm,
                                    double deadbandMinPwm, double minPwm) {
        log.warn("setPWMConfig not implemented yet.");

    }

    public static PWMConfigDataResult getPWMConfigRaw(int pwmPortHandle) {
        log.warn("getPWMConfigRaw not implemented yet.");

        return null;
    }

    public static void setPWMEliminateDeadband(int pwmPortHandle, boolean eliminateDeadband) {
        log.warn("setPWMEliminateDeadband not implemented yet.");

    }

    public static boolean getPWMEliminateDeadband(int pwmPortHandle) {
        log.warn("getPWMEliminateDeadband not implemented yet.");

        return false;
    }

    public static void setPWMRaw(int pwmPortHandle, short value) {
        log.warn("setPWMRaw not implemented yet.");

    }

    public static void setPWMSpeed(int pwmPortHandle, double speed) {
        log.warn("setPWMSpeed not implemented yet.");

    }

    public static void setPWMPosition(int pwmPortHandle, double position) {
        log.warn("setPWMPosition not implemented yet.");

    }

    public static short getPWMRaw(int pwmPortHandle) {
        log.warn("getPWMRaw not implemented yet.");

        return 0;
    }

    public static double getPWMSpeed(int pwmPortHandle) {
        log.warn("getPWMSpeed not implemented yet.");

        return 0;
    }

    public static double getPWMPosition(int pwmPortHandle) {
        log.warn("getPWMPosition not implemented yet.");

        return 0;
    }

    public static void setPWMDisabled(int pwmPortHandle) {
        log.warn("setPWMDisabled not implemented yet.");
    }

    public static void latchPWMZero(int pwmPortHandle) {
        log.warn("latchPWMZero not implemented yet.");

    }

    public static void setPWMPeriodScale(int pwmPortHandle, int squelchMask) {
        log.warn("setPWMPeriodScale not implemented yet.");

    }

}

