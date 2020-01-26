package frc.robot.simulator.hal;

import edu.wpi.first.hal.AllianceStationID;
import edu.wpi.first.hal.ControlWord;
import edu.wpi.first.hal.JNIWrapper;
import edu.wpi.first.hal.MatchInfoData;
import frc.robot.simulator.sim.SimHAL;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

/**
 * Sim JNI classes must be structured EXACTLY like their counterpart non-sim JNI classes. If a single field or method is added or removed, the ByteBuddy redefine will fail
 */
public final class SimHALJNI extends JNIWrapper {
    public static void waitForDSData() {
        SimHAL.waitForDSData();
    }

    public static boolean initialize(int timeout, int mode) {
        return SimHAL.initialize(timeout, mode);
    }

    public static boolean hasMain() {
        return SimHAL.hasMain();
    }

    public static void runMain() {
        SimHAL.runMain();
    }

    public static void exitMain() {
        SimHAL.exitMain();
    }

    public static void observeUserProgramStarting() {
        SimHAL.observeUserProgramStarting();
    }

    public static void observeUserProgramDisabled() {
        SimHAL.observeUserProgramDisabled();
    }

    public static void observeUserProgramAutonomous() {
        SimHAL.observeUserProgramAutonomous();
    }

    public static void observeUserProgramTeleop() {
        SimHAL.observeUserProgramTeleop();
    }

    public static void observeUserProgramTest() {
        SimHAL.observeUserProgramTest();
    }

    public static void report(int resource, int instanceNumber) {
        report(resource, instanceNumber, 0, "");
    }

    public static void report(int resource, int instanceNumber, int context) {
        report(resource, instanceNumber, context, "");
    }

    /**
     * Report the usage of a resource of interest. <br>
     *
     * <p>Original signature: <code>uint32_t report(tResourceType, uint8_t, uint8_t, const
     * char*)</code>
     *
     * @param resource       one of the values in the tResourceType above (max value 51). <br>
     * @param instanceNumber an index that identifies the resource instance. <br>
     * @param context        an optional additional context number for some cases (such as module
     *                       number). Set to 0 to omit. <br>
     * @param feature        a string to be included describing features in use on a specific
     *                       resource. Setting the same resource more than once allows you to change
     *                       the feature string.
     */
    public static int report(int resource, int instanceNumber, int context, String feature) {
        return SimHAL.report(resource, instanceNumber, context, feature);
    }

    public static int nativeGetControlWord() {
        return SimHAL.nativeGetControlWord();
    }

    @SuppressWarnings("JavadocMethod")
    public static void getControlWord(ControlWord controlWord) {
        int word = nativeGetControlWord();
        try {
            Method updateMethod = controlWord.getClass().getDeclaredMethod("update",
                    boolean.class,
                    boolean.class,
                    boolean.class,
                    boolean.class,
                    boolean.class,
                    boolean.class
                    );
            updateMethod.setAccessible(true);
            updateMethod.invoke(controlWord, (word & 1) != 0, ((word >> 1) & 1) != 0, ((word >> 2) & 1) != 0,
                    ((word >> 3) & 1) != 0, ((word >> 4) & 1) != 0, ((word >> 5) & 1) != 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int nativeGetAllianceStation() {
        return SimHAL.nativeGetAllianceStation();
    }

    @SuppressWarnings("JavadocMethod")
    public static AllianceStationID getAllianceStation() {
        switch (nativeGetAllianceStation()) {
            case 0:
                return AllianceStationID.Red1;
            case 1:
                return AllianceStationID.Red2;
            case 2:
                return AllianceStationID.Red3;
            case 3:
                return AllianceStationID.Blue1;
            case 4:
                return AllianceStationID.Blue2;
            case 5:
                return AllianceStationID.Blue3;
            default:
                return null;
        }
    }

    @SuppressWarnings("JavadocMethod")
    public static boolean isNewControlData() {
        return SimHAL.isNewControlData();
    }

    @SuppressWarnings("JavadocMethod")
    public static void releaseDSMutex() {
        SimHAL.releaseDSMutex();
    }

    @SuppressWarnings("JavadocMethod")
    public static boolean waitForDSDataTimeout(double timeout) {
        return SimHAL.waitForDSDataTimeout(timeout);
    }

    public static int kMaxJoystickAxes = 12;
    public static int kMaxJoystickPOVs = 12;

    public static short getJoystickAxes(byte joystickNum, float[] axesArray) {
        return SimHAL.getJoystickAxes(joystickNum, axesArray);
    }

    public static short getJoystickPOVs(byte joystickNum, short[] povsArray) {
        return SimHAL.getJoystickPOVs(joystickNum, povsArray);
    }

    public static int getJoystickButtons(byte joystickNum, ByteBuffer count) {
        return SimHAL.getJoystickButtons(joystickNum, count);
    }

    public static int setJoystickOutputs(byte joystickNum, int outputs, short leftRumble,
                                         short rightRumble) {
        return SimHAL.setJoystickOutputs(joystickNum, outputs, leftRumble, rightRumble);
    }

    public static int getJoystickIsXbox(byte joystickNum) {
        return SimHAL.getJoystickIsXbox(joystickNum);
    }

    public static int getJoystickType(byte joystickNum) {
        return SimHAL.getJoystickType(joystickNum);
    }

    public static String getJoystickName(byte joystickNum) {
        return SimHAL.getJoystickName(joystickNum);
    }

    public static int getJoystickAxisType(byte joystickNum, byte axis) {
        return SimHAL.getJoystickAxisType(joystickNum, axis);
    }

    public static double getMatchTime() {
        return SimHAL.getMatchTime();
    }

    public static boolean getSystemActive() {
        return SimHAL.getSystemActive();
    }

    public static boolean getBrownedOut() {
        return SimHAL.getBrownedOut();
    }

    public static int getMatchInfo(MatchInfoData info) {
        return SimHAL.getMatchInfo(info);
    }

    public static int sendError(boolean isError, int errorCode, boolean isLVCode,
                                String details, String location, String callStack,
                                boolean printMsg) {
        return SimHAL.sendError(isError, errorCode, isLVCode, details, location, callStack, printMsg);
    }

    public static int getPortWithModule(byte module, byte channel) {
        return SimHAL.getPortWithModule(module, channel);
    }

    public static int getPort(byte channel) {
        return SimHAL.getPort(channel);
    }

    private SimHALJNI() {

    }
}
