package frc.robot.simulator.network;

import frc.robot.simulator.network.proto.PingProto;
import frc.robot.simulator.network.proto.PingServiceGrpc;
import frc.robot.simulator.network.proto.RobotProto;
import frc.robot.simulator.network.proto.RobotServiceGrpc;
import frc.robot.simulator.sim.JoystickData;
import frc.robot.simulator.sim.events.EventManager;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class NetworkClient implements Client {
    private static final Logger log = LoggerFactory.getLogger(NetworkClient.class);

    private interface Request {
        void send();
    }

    private ManagedChannel channel;
    private RobotServiceGrpc.RobotServiceStub robotService;
    private PingServiceGrpc.PingServiceBlockingStub pingServiceBlockingStub;
    private RobotServiceGrpc.RobotServiceBlockingStub robotServiceBlockingStub;

    // streams to open on connect()
    private StreamObserver<RobotProto.InputRequest> inputRequestStream;

    /**
     * Is this client connected
     */
    private boolean connected = false;

    private final String host;
    private final int port;

    // Robot state info
    private RobotProto.InputRequest inputRequest = RobotProto.InputRequest.getDefaultInstance();

    public NetworkClient(String host, int port) {
        this.host = host;
        this.port = port;
        init();
    }

    private void init() {
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext()
                .build();

        pingServiceBlockingStub = PingServiceGrpc.newBlockingStub(channel).withWaitForReady();
        robotService = RobotServiceGrpc.newStub(channel);
        robotServiceBlockingStub = RobotServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public void shutdown() throws InterruptedException {
        connected = false;
        disconnect();
        channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
    }

    @Override
    public void waitForServer() {
        log.info("Attempting to connect to server: " + this.host + ":" + this.port);
        var response = pingServiceBlockingStub.ping(PingProto.PingMessage.newBuilder().build());
        log.info("Client has connected to server: " + this.host + ":" + this.port);

        connected = true;
    }

    /**
     * Make a synchronous call to update the motor
     *
     * @param motorConfig
     */
    @Override
    public void updateMotor(RobotProto.MotorConfig motorConfig) {
        robotServiceBlockingStub.updateMotor(motorConfig);
        EventManager.publish(motorConfig);
    }

    @Override
    public void updateRobotState(RobotProto.RobotState robotState) {
        robotServiceBlockingStub.robot(robotState);
    }

    /**
     * Send a joystick update to the server
     *
     * @param joystickData
     */
    @Override
    public void inputUpdate(JoystickData joystickData) {

        RobotProto.InputRequest.Builder builder = RobotProto.InputRequest.newBuilder();
        builder.setId(joystickData.id);

        for (int i = 0; i < joystickData.axes.length; i++) {
            builder.addAxes(joystickData.axes[i]);
        }

        for (int i = 0; i < joystickData.povs.length; i++) {
            builder.addPovs(joystickData.povs[i]);
        }

        for (int i = 0; i < joystickData.buttons.length; i++) {
            builder.addButtons(joystickData.buttons[i]);
        }

        RobotProto.InputRequest newRequest = builder.build();
        // send this request to the server
        sendRequest(() -> {
            if (inputRequestStream == null) {
                log.info("Connecting to input stream: " + this.host + ":" + this.port);
                inputRequestStream = robotService.input(new EmptyStreamObserver());
            }

            if (!newRequest.equals(inputRequest)) {
                inputRequest = newRequest;
                inputRequestStream.onNext(inputRequest);
            }
        });
    }

    /**
     * Disconnect the server and stop any streams
     */
    private void disconnect() {
        connected = false;

        completeStream(inputRequestStream);

        inputRequestStream = null;
    }

    /**
     * Complete a stream, ignoring any errors
     *
     * @param stream
     */
    private void completeStream(StreamObserver stream) {
        if (stream != null) {
            try {
                stream.onCompleted();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    private void sendRequest(Request requestFunction) {
        try {
            if (connected) {
                requestFunction.send();
            }
        } catch (StatusRuntimeException e) {
            switch (e.getStatus().getCode()) {
                case UNKNOWN:
                    // server threw an error
                    log.error("Server threw an error", e);
                    break;
                case UNAVAILABLE:
                    // server shutting down
                    log.error("Server unavailable, disconnecting and waiting for server to come back");
                    disconnect();
                    waitForServer();
                    break;
                default:
                    throw e;
            }
        }
    }
}
