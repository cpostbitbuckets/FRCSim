package frc.robot.simulator.sim;

/**
 * A place to store settings for the simulator
 */
public class SimulatorSettings {

    private static final int javaServerPort = 50051;
    private static final int unityServerPort = 50052;

    public enum Mode {
        Local,
        JavaServer,
        UnityServer
    }

    // the mode of this simulator
    private final Mode mode;

    // set to true to create a new UI
    private final boolean createUi;

    // the class of the robot to load
    private final String robotClass;

    private final String configFile;

    private final int clientPort;
    private final int inputClientPort;

    /**
     * The settings for the simulator are all command line properties with sensible defaults
     */
    public SimulatorSettings() {
        robotClass = System.getProperty("robotClass", "frc.robot.Robot");

        // Check if we should simulate motors. For Unity sims, we want Unity to simulate the motors so it can apply the results
        // against the sim physics and return results. For a pure java UI sim, we want our java simulator to simulate motors
        mode = Mode.valueOf(System.getProperty("mode", "Local"));

        // create a UI, if the user doesn't want a headless system
        createUi = "true".equals(System.getProperty("ui", "true"));

        // the config file for motor settings and other robot specific config
        configFile = System.getProperty("configFile", "FRCSim/config.yaml");

        switch (mode) {

            case JavaServer:
                clientPort = javaServerPort;
                inputClientPort = javaServerPort;
                break;
            case UnityServer:
                clientPort = unityServerPort;
                inputClientPort = javaServerPort;
                break;
            default:
                clientPort = inputClientPort = -1;
                break;
        }
    }

    public Mode getMode() {
        return mode;
    }

    public boolean isCreateUi() {
        return createUi;
    }

    public String getRobotClass() {
        return robotClass;
    }

    public String getConfigFile() {
        return configFile;
    }

    public int getClientPort() {
        return clientPort;
    }

    public int getInputClientPort() {
        return inputClientPort;
    }
}
