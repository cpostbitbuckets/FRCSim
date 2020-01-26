package frc.robot.simulator.network.services;

import com.google.inject.Inject;
import com.google.protobuf.Empty;
import frc.robot.simulator.network.proto.RobotProto;
import frc.robot.simulator.network.proto.RobotServiceGrpc;
import frc.robot.simulator.network.services.streams.LoggingStreamObserver;
import frc.robot.simulator.sim.JoystickData;
import frc.robot.simulator.sim.SimHAL;
import frc.robot.simulator.sim.SimulatorThread;
import frc.robot.simulator.sim.events.ConnectRobotEvent;
import frc.robot.simulator.sim.events.EventManager;
import frc.robot.simulator.sim.motors.MotorSimulator;
import frc.robot.simulator.sim.motors.MotorStore;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The RobotService is the main server service called by robot clients
 */
public class RobotService extends RobotServiceGrpc.RobotServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(RobotService.class);

    // these variables are all for the motor simulation threads
    private final SimulatorThread simulatorThread;

    private final MotorStore motorStore;

    // these reequest stream are opened by clients to send requests to the server
    private StreamObserver<RobotProto.InputRequest> inputRequestStream;
    private StreamObserver<RobotProto.MotorOutputs> motorOutputsRequestStream;

    // the current state of the robot, i.e. enabled, running in autonomous, etc.
    private RobotProto.RobotState robotState = RobotProto.RobotState.newBuilder().build();


    @Inject
    public RobotService(SimulatorThread simulatorThread, MotorStore motorStore) {
        this.simulatorThread = simulatorThread;
        this.motorStore = motorStore;
    }

    /**
     * Open up a stream to receive joystick/keyboard inputs from a client
     * @param responseObserver
     * @return
     */
    @Override
    public StreamObserver<RobotProto.InputRequest> input(StreamObserver<Empty> responseObserver) {
        // if a new client requests an input stream, close the old one so the new one can open
        // We support inputs from both unity and java clients at the same time
        if (inputRequestStream != null) {
            inputRequestStream.onCompleted();
        }
        inputRequestStream = new LoggingStreamObserver<>("inputRequestStream") {
            @Override
            public void onNext(RobotProto.InputRequest request) {
                // Received new joystick data from a client
                JoystickData joystickData = new JoystickData();
                joystickData.id = request.getId();
                for (int i = 0; i < request.getAxesCount(); i++) {
                    joystickData.axes[i] = request.getAxes(i);
                }
                for (int i = 0; i < request.getPovsCount(); i++) {
                    joystickData.povs[i] = request.getPovs(i);
                }
                for (int i = 0; i < request.getButtonsCount(); i++) {
                    joystickData.buttons[i] = request.getButtons(i);
                }

//                log.info("Received joystick update from the client, updating SimHAL");
                SimHAL.setJoystick(joystickData.id, joystickData);
            }

            @Override
            public void onError(Throwable t) {
                if (t instanceof StatusException) {
                    StatusException e = (StatusException)t;
                    switch (e.getStatus().getCode()) {
                        case CANCELLED:
                            // client cancelled stream...
                            // this shouldn't happen on the client
                            log.error("Client closed input request stream, closing connection.");
                            try {
                                inputRequestStream.onCompleted();
                            } catch(Exception ex) {
                              // ignore this
                            } finally {
                                inputRequestStream = null;
                            }
                            break;
                        case UNKNOWN:
                            // server threw an error
                            log.error("Server threw an error", e);
                            break;
                        case UNAVAILABLE:
                            // server shutting down
                            log.error("Server unavailable, waiting for server to come back");
                            break;
                        default:
                            super.onError(t);
                    }
                }
                log.error("Client closed input request stream, closing connection.");
            }
        };
        return inputRequestStream;
    }

    @Override
    public StreamObserver<RobotProto.MotorOutputs> updateMotorOutputs(StreamObserver<Empty> responseObserver) {
        // if a new client requests to open a new motorOutputs stream, close the old one so the new one can open
        if (motorOutputsRequestStream != null) {
            motorOutputsRequestStream.onCompleted();
        }

        motorOutputsRequestStream = new LoggingStreamObserver<>("motorOutputsRequestStream") {
            @Override
            public void onNext(RobotProto.MotorOutputs request) {
                // a client sent us a new set of motor outputs so publish it
                EventManager.publish(request);
            }

            @Override
            public void onError(Throwable t) {
                if (t instanceof StatusException) {
                    StatusException e = (StatusException)t;
                    switch (e.getStatus().getCode()) {
                        case CANCELLED:
                            // client cancelled stream...
                            // this shouldn't happen on the client
                            log.error("Client closed motorOutputs request stream, closing connection.");
                            try {
                                motorOutputsRequestStream.onCompleted();
                            } catch(Exception ex) {
                                // ignore this
                            } finally {
                                motorOutputsRequestStream = null;
                            }
                            break;
                        default:
                            super.onError(t);
                    }
                }
            }
        };
        return motorOutputsRequestStream;
    }

    @Override
    public void updateMotor(RobotProto.MotorConfig config, StreamObserver<Empty> responseObserver) {
        motorStore.updateMotorConfig(config);
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }



    @Override
    public void robot(RobotProto.RobotState request, StreamObserver<Empty> responseObserver) {
        handleRobotStateUpdate(request);
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    /**
     * This is called when the unity server wants to reconnect to the robot
     * @param request
     * @param responseObserver
     */
    @Override
    public void connectToRobot(Empty request, StreamObserver<Empty> responseObserver) {
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
        EventManager.publish(new ConnectRobotEvent());
    }

    /**
     * When the robot state has changed, this method is called
     * @param newState The new state we are updating to
     */
    private void handleRobotStateUpdate(RobotProto.RobotState newState) {
        if (robotState.getEnabled() && !newState.getEnabled()) {
            simulatorThread.stopSimulationThread();
        }

        // check if the robot was just initialized
        if (!robotState.getInitialized() && newState.getInitialized()) {
            log.info("Robot has been initialized");
            newState = newState.toBuilder().setEnabled(true).build();

            simulatorThread.startSimulationThread();
        }

        robotState = newState;
    }



}
