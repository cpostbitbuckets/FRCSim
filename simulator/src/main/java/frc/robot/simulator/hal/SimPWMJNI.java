package frc.robot.simulator.hal;

import edu.wpi.first.hal.DIOJNI;
import edu.wpi.first.hal.PWMConfigDataResult;
import frc.robot.simulator.sim.SimPWM;

/**
 * Sim JNI classes must be structured EXACTLY like their counterpart non-sim JNI classes. If a single field or method is added or removed, the ByteBuddy redefine will fail
 */
public class SimPWMJNI extends DIOJNI {

    public static int initializePWMPort(int halPortHandle) {
        return SimPWM.initializePWMPort(halPortHandle);
    }

    public static boolean checkPWMChannel(int channel) {
        return SimPWM.checkPWMChannel(channel);
    }

    public static void freePWMPort(int pwmPortHandle) {
        SimPWM.freePWMPort(pwmPortHandle);
    }

    public static void setPWMConfigRaw(int pwmPortHandle, int maxPwm,
                                       int deadbandMaxPwm, int centerPwm,
                                       int deadbandMinPwm, int minPwm) {
        SimPWM.setPWMConfigRaw(pwmPortHandle, maxPwm, deadbandMaxPwm, centerPwm, deadbandMinPwm, minPwm);
    }

    public static void setPWMConfig(int pwmPortHandle, double maxPwm,
                                    double deadbandMaxPwm, double centerPwm,
                                    double deadbandMinPwm, double minPwm) {
        SimPWM.setPWMConfig(pwmPortHandle, maxPwm, deadbandMaxPwm, centerPwm, deadbandMinPwm, minPwm);
    }

    public static PWMConfigDataResult getPWMConfigRaw(int pwmPortHandle) {
        return SimPWM.getPWMConfigRaw(pwmPortHandle);
    }

    public static void setPWMEliminateDeadband(int pwmPortHandle, boolean eliminateDeadband) {
        SimPWM.setPWMEliminateDeadband(pwmPortHandle, eliminateDeadband);
    }

    public static boolean getPWMEliminateDeadband(int pwmPortHandle) {
        return SimPWM.getPWMEliminateDeadband(pwmPortHandle);
    }

    public static void setPWMRaw(int pwmPortHandle, short value) {
        SimPWM.setPWMRaw(pwmPortHandle, value);
    }

    public static void setPWMSpeed(int pwmPortHandle, double speed) {
        SimPWM.setPWMSpeed(pwmPortHandle, speed);
    }

    public static void setPWMPosition(int pwmPortHandle, double position) {
        SimPWM.setPWMPosition(pwmPortHandle, position);
    }

    public static short getPWMRaw(int pwmPortHandle) {
        return SimPWM.getPWMRaw(pwmPortHandle);
    }

    public static double getPWMSpeed(int pwmPortHandle) {
        return SimPWM.getPWMSpeed(pwmPortHandle);
    }

    public static double getPWMPosition(int pwmPortHandle) {
        return SimPWM.getPWMPosition(pwmPortHandle);
    }

    public static void setPWMDisabled(int pwmPortHandle) {
        SimPWM.setPWMDisabled(pwmPortHandle);
    }

    public static void latchPWMZero(int pwmPortHandle) {
        SimPWM.latchPWMZero(pwmPortHandle);
    }

    public static void setPWMPeriodScale(int pwmPortHandle, int squelchMask) {
        SimPWM.setPWMPeriodScale(pwmPortHandle, squelchMask);
    }
}
