package frc.robot.simulator.sim;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import frc.robot.simulator.network.Client;
import frc.robot.simulator.network.LocalClient;
import frc.robot.simulator.network.NetworkClient;
import frc.robot.simulator.network.Server;
import frc.robot.simulator.network.services.RobotService;
import frc.robot.simulator.sim.config.serialization.ConfigReader;
import frc.robot.simulator.sim.config.SimulatorConfig;
import frc.robot.simulator.sim.motors.MotorSimulator;
import frc.robot.simulator.sim.motors.MotorStore;
import frc.robot.simulator.sim.ui.SimWindow;
import frc.robot.simulator.sim.utils.VendorUtils;

import java.io.IOException;

/**
 * The simulator injection module for guice
 */
public class SimulatorModule extends AbstractModule {

    @Override
    protected void configure() {
        // wire up the injected dependencies for these static classes
        // these classes must be static because they are called by bytecode replaced
        // static classes that cannot be modified (no new fields or methods)
        if (VendorUtils.isCtreAvailable()) {
            requestStaticInjection(SimMotController.class);
        }
        requestStaticInjection(SimHAL.class);
        requestStaticInjection(SimCANSparkMax.class);
        requestStaticInjection(SimSolenoid.class);
    }

    /**
     * Return an instance of the simulator config using the configReader
     * @param configReader
     * @return
     * @throws IOException
     */
    @Provides
    @Singleton
    SimulatorConfig simulatorConfig(ConfigReader configReader) throws IOException {
        return configReader.loadConfig();
    }

    /**
     * Create a server unless we are on local mode
     * @param settings
     * @param robotService
     * @return
     */
    @Provides
    @Singleton
    Server server(SimulatorSettings settings, RobotService robotService) {
        if (settings.getMode() != SimulatorSettings.Mode.Local) {
            return new Server(robotService);
        } else {
            return null;
        }
    }

    @Provides
    @Singleton
    @Named("Client")
    Client client(SimulatorSettings settings, SimulatorThread simulatorThread, MotorStore motorStore) {
        if (settings.getMode() == SimulatorSettings.Mode.Local) {
            return new LocalClient(simulatorThread, motorStore);
        } else {
            return new NetworkClient("localhost", settings.getClientPort());
        }
    }

    @Provides
    @Singleton
    @Named("InputClient")
    Client inputClient(SimulatorSettings settings, @Named("Client") Client client) {
        if (settings.getMode() == SimulatorSettings.Mode.Local) {
            // if we are local, use the same client as the regular client
            return client;
        } else {
            return new NetworkClient("localhost", settings.getInputClientPort());
        }
    }

    /**
     * Create a new UI if we are configured for it
     * @param settings
     * @param simulatorConfig
     * @return
     */
    @Provides
    @Singleton
    SimWindow ui(SimulatorSettings settings, SimulatorConfig simulatorConfig, @Named("InputClient") Client inputClient) {
        if (settings.isCreateUi()) {
            return new SimWindow(simulatorConfig, inputClient);
        } else {
            return null;
        }
    }
}
