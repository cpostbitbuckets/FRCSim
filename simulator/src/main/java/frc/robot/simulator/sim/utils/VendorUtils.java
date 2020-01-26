package frc.robot.simulator.sim.utils;

import org.apache.commons.lang3.ClassUtils;

public class VendorUtils {
    public static final String ctreControllerClassName = "com.ctre.phoenix.motorcontrol.can.MotControllerJNI";
    public static final String ctreWrapperClassName = "com.ctre.phoenix.CTREJNIWrapper";
    public static final String simCTREControllerClassName = "frc.robot.simulator.hal.ctre.SimMotControllerJNI";
    public static final String simCTREWrapperClassName = "frc.robot.simulator.hal.ctre.SimCTREJNIWrapper";

    public static Class ctreControllerClass;
    public static Class ctreWrapperClass;
    public static Class simCTREControllerClass;
    public static Class simCTREWrapperClass;

    public static final String revControllerClassName = "com.revrobotics.jni.CANSparkMaxJNI";
    public static final String revWrapperClassName = "com.revrobotics.jni.RevJNIWrapper";
    public static final String simRevControllerClassName = "frc.robot.simulator.hal.rev.SimCANSparkMaxJNI";
    public static final String simRevWrapperClassName = "frc.robot.simulator.hal.rev.SimRevJNIWrapper";

    public static Class revControllerClass;
    public static Class revWrapperClass;
    public static Class simRevControllerClass;
    public static Class simRevWrapperClass;
    private static boolean ctreAvailable;
    private static boolean revAvailable;

    static {
        try {
            ctreControllerClass = ClassUtils.getClass(ctreControllerClassName, false);
            ctreWrapperClass = ClassUtils.getClass(ctreWrapperClassName, false);
            simCTREControllerClass = ClassUtils.getClass(simCTREControllerClassName, false);
            simCTREWrapperClass = ClassUtils.getClass(simCTREWrapperClassName, false);
            ctreAvailable = true;
        } catch (ClassNotFoundException e) {
            ctreAvailable = false;
        }

        try {
            revControllerClass = ClassUtils.getClass(revControllerClassName, false);
            revWrapperClass = ClassUtils.getClass(revWrapperClassName, false);
            simRevControllerClass = ClassUtils.getClass(simRevControllerClassName, false);
            simRevWrapperClass = ClassUtils.getClass(simRevWrapperClassName, false);
            revAvailable = true;
        } catch (ClassNotFoundException e) {
            revAvailable = false;
        }
    }

    public static boolean isCtreAvailable() {
        return ctreAvailable;
    }

    public static boolean isRevAvailable() {
        return revAvailable;
    }
}
