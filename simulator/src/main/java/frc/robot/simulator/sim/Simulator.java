package frc.robot.simulator.sim;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import frc.robot.simulator.network.Client;
import frc.robot.simulator.network.Server;
import frc.robot.simulator.sim.config.serialization.ConfigWriter;
import frc.robot.simulator.sim.events.ConnectRobotEvent;
import frc.robot.simulator.sim.events.EventManager;
import frc.robot.simulator.sim.events.RobotInitializedEvent;
import frc.robot.simulator.sim.motors.MotorSimulator;
import frc.robot.simulator.sim.motors.MotorStore;
import frc.robot.simulator.sim.motors.SimMotor;
import frc.robot.simulator.sim.ui.SimWindow;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;

/**
 * The main entry point for our simulator. This starts the UI, motor simulator threads, etc.
 */
@Singleton
public class Simulator {
    private static final Logger log = LoggerFactory.getLogger(Simulator.class);

    // injected dependencies
    private final SimulatorSettings simulatorSettings;

    private final ConfigWriter configWriter;

    // the simulator runs a server to receive inputs from a client
    private final Server server;

    /**
     * Optional UI to update
     */
    private final SimWindow ui;

    // something to simulate motors
    private final MotorStore motorStore;

    // the simulator also writes stuff to the server
    private final Client client;

    // the client to use to send input updates to the server
    // this is always java
    private final Client inputClient;

    @Inject
    public Simulator(SimulatorSettings simulatorSettings,
                     ConfigWriter configWriter,
                     MotorStore motorStore,
                     @Named("Client") Client client,
                     @Named("InputClient") Client inputClient,
                     @Nullable Server server,
                     @Nullable SimWindow ui) {
        this.simulatorSettings = simulatorSettings;
        this.configWriter = configWriter;
        this.motorStore = motorStore;
        this.client = client;
        this.inputClient = inputClient;
        this.server = server;
        this.ui = ui;
    }

    public void start() throws IOException {
        // this is required by startRobot
        final File file = new File("/tmp/frc_versions");
        file.mkdirs();

        // if we aren't doing local only, create a server and tell our clients to wait for the server
        if (simulatorSettings.getMode() != SimulatorSettings.Mode.Local) {
            // start a server to communicate with
            server.start();

            // wait for the server to come online and then start the client listening
            // to motor output updates
            log.info("Waiting for input server " + simulatorSettings.getInputClientPort() + " to come online" );
            inputClient.waitForServer();
            log.info("InputServer is up " + simulatorSettings.getInputClientPort());

            log.info("Waiting for server " + simulatorSettings.getClientPort() + " to come online" );
            client.waitForServer();
            log.info("MotorServer is up " + simulatorSettings.getClientPort());
        }

        // have the simulator subscribe to robot events
        EventManager.subscribeToConnectRobotEvents(this::onConnectRobot);
        EventManager.subscribeToRobotInitializedEvents(this::onRobotInitialized);
    }

    public void stop() throws InterruptedException {
        if (client != null) {
            client.shutdown();
        }

        if (server != null) {
            server.stop();
        }

        final File file = new File("/tmp/frc_versions");
        try {
            FileUtils.deleteDirectory(file);
        } catch (IOException e) {
            log.error("Failed to delete " + file);
        }

    }

    private void onRobotInitialized(RobotInitializedEvent robotInitializedEvent) {
        if (simulatorSettings.isCreateUi()) {
            java.awt.EventQueue.invokeLater(() -> {
                try {
                    ui.create(motorStore.getSimMotorsSorted());
                } catch (IOException e) {
                    log.error("Failed to create UI.", e);
                }
            });
        }

        // save the config after robot init
        configWriter.saveConfig();
    }

    private void onConnectRobot(ConnectRobotEvent event) {
        log.info("Unity Client has connected (again possibly), sending motor/state data to server");

        // must be in id order because of followers
        // TODO: fix follower dependencies, or make a "create time" for SimMotors and sort by that
        for (SimMotor motor : motorStore.getSimMotorsSorted()) {
            log.info("Sending motor " + motor.getId() + " data back to Unity client.");
            client.updateMotor(motor.getConfig());
        }

        log.info("Sending robot state to Unity client.");
        client.updateRobotState(SimHAL.getRobotState());

        // enable the robot again
        SimHAL.getDriverStation().enable();
    }
}
