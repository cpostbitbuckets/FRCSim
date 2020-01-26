package frc.robot.simulator.hal;

import edu.wpi.first.hal.HALUtil;
import edu.wpi.first.hal.JNIWrapper;
import frc.robot.simulator.sim.SimHALUtil;

/**
 * Sim JNI classes must be structured EXACTLY like their counterpart non-sim JNI classes. If a single field or method is added or removed, the ByteBuddy redefine will fail
 */
public final class SimHALUtilJNI extends JNIWrapper {
    public static final int NULL_PARAMETER = HALUtil.NULL_PARAMETER;
    public static final int SAMPLE_RATE_TOO_HIGH = HALUtil.SAMPLE_RATE_TOO_HIGH;
    public static final int VOLTAGE_OUT_OF_RANGE = HALUtil.VOLTAGE_OUT_OF_RANGE;
    public static final int LOOP_TIMING_ERROR = HALUtil.LOOP_TIMING_ERROR;
    public static final int INCOMPATIBLE_STATE = HALUtil.INCOMPATIBLE_STATE;
    public static final int ANALOG_TRIGGER_PULSE_OUTPUT_ERROR = HALUtil.ANALOG_TRIGGER_PULSE_OUTPUT_ERROR;
    public static final int NO_AVAILABLE_RESOURCES = HALUtil.NO_AVAILABLE_RESOURCES;
    public static final int PARAMETER_OUT_OF_RANGE = HALUtil.PARAMETER_OUT_OF_RANGE;

    public static short getFPGAVersion() {
        return SimHALUtil.getFPGAVersion();
    }

    public static int getFPGARevision() {
        return SimHALUtil.getFPGARevision();
    }

    public static long getFPGATime() {
        return SimHALUtil.getFPGATime();
    }

    public static int getHALRuntimeType() {
        return SimHALUtil.getHALRuntimeType();
    }

    public static boolean getFPGAButton() {
        return SimHALUtil.getFPGAButton();
    }

    public static String getHALErrorMessage(int code) {
        return SimHALUtil.getHALErrorMessage(code);
    }

    public static int getHALErrno() {
        return SimHALUtil.getHALErrno();
    }

    public static String getHALstrerror(int errno) {
        return SimHALUtil.getHALstrerror(errno);
    }

    public static String getHALstrerror() {
        return getHALstrerror(getHALErrno());
    }

    private SimHALUtilJNI() {

    }
}

