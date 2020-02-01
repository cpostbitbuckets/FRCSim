
package frc.robot.simulator.sim;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import edu.wpi.first.hal.AllianceStationID;
import edu.wpi.first.hal.ControlWord;
import edu.wpi.first.hal.MatchInfoData;
import frc.robot.simulator.network.Client;
import frc.robot.simulator.network.proto.RobotProto;
import frc.robot.simulator.sim.events.EventManager;
import frc.robot.simulator.sim.events.RobotInitializedEvent;
import frc.robot.simulator.sim.solenoids.SolenoidStore;
import frc.robot.simulator.sim.ui.XboxConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

public class SimHAL {
    private static final Logger log = LoggerFactory.getLogger(SimHAL.class);

    @Inject @Named("Client") static Client client;
    @Inject static SolenoidStore solenoidStore;

    private static DriverStation driverStation = new DriverStation();

    private static RobotProto.RobotState robotState = RobotProto.RobotState.newBuilder().build();

    private static JoystickData[] joysticks = new JoystickData[] {
            new JoystickData(),
            new JoystickData(),
    };

    public static RobotProto.RobotState getRobotState() {
        return robotState;
    }

    public static DriverStation getDriverStation() {
        return driverStation;
    }

    public static JoystickData getJoystick(int id) {
        return joysticks[id];
    }

    public static void setJoystick(int id, JoystickData joystickData) {
        joysticks[id] = joystickData;
    }


    public static void waitForDSData() {
        // This waits for the driver station to have new data
        // we are waiting 10 ms, but maybe we should do some event based stuff?
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            log.error("Failed to sleep while waiting for DS data");
        }
    }

    public static boolean initialize(int timeout, int mode) {
        // we don't do any driverstation initialization yet
        return true;
    }

    public static boolean hasMain() {
        // we don't have a main, just run the robot like before (2019)
        return false;
    }

    public static void runMain() {
        log.warn("runMain not implemented yet.");
    }

    public static void exitMain() {
        log.warn("exitMain not implemented yet.");
    }


    public static void observeUserProgramStarting() {
        robotState = RobotProto.RobotState.newBuilder().setInitialized(true).build();
        // notify the server that the robot is initialized
        client.updateRobotState(robotState);

        // tell any subscribers about our robot initialize event
        log.info("Informed server that robot has been initialized, notifying subscribers");
        EventManager.publish(new RobotInitializedEvent(robotState));

        // once robotInit() is done, enable the driver station so our
        // simulated robot will start
        driverStation.enable();
    }

    public static void observeUserProgramDisabled() {
        // called every loop when the robot is disabled
    }

    public static void observeUserProgramAutonomous() {
        // called every loop when the robot is in autonomous
    }

    public static void observeUserProgramTeleop() {
        // called every loop when the robot is in teleop
    }

    public static void observeUserProgramTest() {
        // called every loop when the robot is in test
    }

    public static void report(int resource, int instanceNumber) {
        // do nothing
    }

    public static void report(int resource, int instanceNumber, int context) {
        // do nothing
    }

    public static int report(int resource, int instanceNumber, int context, String feature) {
        // do nothing
        return 0;
    }

    public static int nativeGetControlWord() {
        return driverStation.getControlWord();
    }

    public static void getControlWord(ControlWord controlWord) {
        log.warn("getControlWord not implemented yet.");

    }

    public static AllianceStationID getAllianceStation() {
        log.warn("getAllianceStation not implemented yet.");

        return null;
    }

    public static boolean isNewControlData() {
        log.warn("isNewControlData not implemented yet.");

        return false;
    }

    public static void releaseDSMutex() {
        log.warn("releaseDSMutex not implemented yet.");

    }

    public static boolean waitForDSDataTimeout(double timeout) {
        log.warn("waitForDSDataTimeout not implemented yet.");
//        return true;

        return false;
    }

    public static short getJoystickAxes(byte joystickNum, float[] axesArray) {
        if (joystickNum >= joysticks.length) {
            return 0;
        }
        JoystickData joystickData = joysticks[joystickNum];
        for (int i = 0; i < joystickData.axes.length; i++) {
            axesArray[i] = joystickData.axes[i];
        }

        if (axesArray.length > 0) {
            // driver station reports up on the Y axis as inverted. LAME
            axesArray[XboxConstants.LEFT_STICK_Y.getValue()] *= -1.;
            axesArray[XboxConstants.RIGHT_STICK_Y.getValue()] *= -1.;
        }
        return (short) joystickData.axes.length;
    }

    public static short getJoystickPOVs(byte joystickNum, short[] povsArray) {
        if (joystickNum >= joysticks.length) {
            return 0;
        }
        JoystickData joystickData = joysticks[joystickNum];
        for (int i = 0; i < joystickData.povs.length; i++) {
            povsArray[i] = (short) joystickData.povs[i];
        }

        return (short) joystickData.povs.length;
    }

    public static int getJoystickButtons(byte joystickNum, ByteBuffer count) {
        if (joystickNum >= joysticks.length) {
            return 0;
        }
        JoystickData joystickData = joysticks[joystickNum];

        count.clear();
        count.put((byte) joystickData.buttons.length);
        return joystickData.getButtonMask();
    }

    public static int setJoystickOutputs(byte joystickNum, int outputs, short leftRumble,
                                         short rightRumble) {
        log.warn("setJoystickOutputs not implemented yet.");

        return 0;
    }

    public static int getJoystickIsXbox(byte joystickNum) {
        log.warn("getJoystickIsXbox not implemented yet.");

        return 0;
    }

    public static int getJoystickType(byte joystickNum) {
        log.warn("getJoystickType not implemented yet.");

        return 0;
    }

    public static String getJoystickName(byte joystickNum) {
        log.warn("getJoystickName not implemented yet.");

        return null;
    }

    public static int getJoystickAxisType(byte joystickNum, byte axis) {
        log.warn("getJoystickAxisType not implemented yet.");

        return 0;
    }

    public static double getMatchTime() {
        log.warn("getMatchTime not implemented yet.");

        return 0;
    }

    public static boolean getSystemActive() {
        log.warn("getSystemActive not implemented yet.");

//        return true;

        return false;
    }

    public static boolean getBrownedOut() {
        log.warn("getBrownedOut not implemented yet.");

        return false;
    }

    public static int getMatchInfo(MatchInfoData info) {
        // we don't use the match info in our sim
//        info.setData("sim event", "", 1, 0, 0);
        return 0;
    }

    public static int sendError(boolean isError, int errorCode, boolean isLVCode,
                                String details, String location, String callStack,
                                boolean printMsg) {

        // ignore these errors in simulator mode
//        if (!details.contains("Output not updated often enough")) {
            System.err.println(location);
            System.err.println(callStack);
            System.err.println(details);
//        }
        return 0;
    }

    /**
     * This is called for Solenoids to get a port for a solenoid module
     * @param module
     * @param channel
     * @return a handle for this solenoid's channel
     */
    public static int getPortWithModule(byte module, byte channel) {
        return solenoidStore.createSolenoidPort(module, channel);
    }

    public static int getPort(byte channel) {
        log.warn("getPort not implemented yet.");

        return 0;
    }

    public static int nativeGetAllianceStation() {
        return 0;
    }

}
