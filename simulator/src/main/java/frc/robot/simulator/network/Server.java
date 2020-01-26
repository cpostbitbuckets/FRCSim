package frc.robot.simulator.network;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import frc.robot.simulator.network.services.PingService;
import frc.robot.simulator.network.services.RobotService;
import frc.robot.simulator.sim.config.SimulatorConfig;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Singleton
public class Server {
    private static final Logger log = LoggerFactory.getLogger(Server.class);

    private io.grpc.Server server;

    private final RobotService robotService;

    @Inject
    public Server(RobotService robotService) {
        this.robotService = robotService;
    }

    public void start() throws IOException {
        /* The port on which the server should run */
        int port = 50051;

        server = ServerBuilder.forPort(port)
                .addService(new PingService())
                .addService(robotService)
                .build()
                .start();
        log.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the log may have been reset by its JVM shutdown hook.
                log.warn("*** shutting down gRPC server since JVM is shutting down");
                Server.this.stop();
                log.warn("*** server shut down");
            }
        });
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    public RobotService getRobotService() {
        return robotService;
    }
}
