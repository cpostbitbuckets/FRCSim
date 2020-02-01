package frc.robot.simulator.sim.events;

import frc.robot.simulator.network.proto.RobotProto;
import frc.robot.simulator.sim.RobotPosition;
import frc.robot.simulator.sim.solenoids.SimSolenoidPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EventManager {
    private static final Logger log = LoggerFactory.getLogger(EventManager.class);

    private static List<Consumer<RobotProto.MotorOutputs>> motorOutputsSubscribers = new ArrayList<>();
    private static List<Consumer<ConnectRobotEvent>> connectRobotSubscribers = new ArrayList<>();
    private static List<Consumer<RobotInitializedEvent>> robotInitializedSubscribers = new ArrayList<>();
    private static List<Consumer<RobotResetEvent>> robotResetSubscribers = new ArrayList<>();
    private static List<Consumer<RobotProto.MotorConfig>> motorConfigSubscribers = new ArrayList<>();
    private static List<Consumer<RobotPosition>> robotPositionSubscribers = new ArrayList<>();
    private static List<Consumer<SimSolenoidPort>> solenoidSubscribers = new ArrayList<>();

    public static void subscribeToMotorOutputsEvents(Consumer<RobotProto.MotorOutputs> subscriber) {
        motorOutputsSubscribers.add(subscriber);
    }

    public static void subscribeToMotorConfigEvents(Consumer<RobotProto.MotorConfig> subscriber) {
        motorConfigSubscribers.add(subscriber);
    }

    public static void subscribeToConnectRobotEvents(Consumer<ConnectRobotEvent> subscriber) {
        connectRobotSubscribers.add(subscriber);
    }

    public static void subscribeToRobotInitializedEvents(Consumer<RobotInitializedEvent> subscriber) {
        robotInitializedSubscribers.add(subscriber);
    }

    public static void subscribeToRobotPositionEvents(Consumer<RobotPosition> subscriber) {
        robotPositionSubscribers.add(subscriber);
    }

    public static void subscribeToRobotResetEvents(Consumer<RobotResetEvent> subscriber) {
        robotResetSubscribers.add(subscriber);
    }

    public static void subscribeToSolenoidEvents(Consumer<SimSolenoidPort> subscriber) {
        solenoidSubscribers.add(subscriber);
    }

    public static void publish(RobotProto.MotorOutputs motorOutputs) {
        for (var event : motorOutputsSubscribers) {
            try {
                event.accept(motorOutputs);
            } catch (Exception e) {
                log.error("Subscriber failed to handle published event.", e);
            }
        }
    }

    public static void publish(RobotProto.MotorConfig motorConfig) {
        for (var event : motorConfigSubscribers) {
            try {
                event.accept(motorConfig);
            } catch (Exception e) {
                log.error("Subscriber failed to handle published event.", e);
            }
        }
    }

    public static void publish(ConnectRobotEvent connectRobotEvent) {
        for (var event : connectRobotSubscribers) {
            try {
                event.accept(connectRobotEvent);
            } catch (Exception e) {
                log.error("Subscriber failed to handle published event.", e);
            }
        }
    }

    public static void publish(RobotInitializedEvent robotInitializedEvent) {
        for (var event : robotInitializedSubscribers) {
            try {
                event.accept(robotInitializedEvent);
            } catch (Exception e) {
                log.error("Subscriber failed to handle published event.", e);
            }
        }
    }

    public static void publish(RobotResetEvent robotResetEvent) {
        for (var event : robotResetSubscribers) {
            try {
                event.accept(robotResetEvent);
            } catch (Exception e) {
                log.error("Subscriber failed to handle published event.", e);
            }
        }
    }

    public static void publish(RobotPosition robotPosition) {
        for (var event : robotPositionSubscribers) {
            try {
                event.accept(robotPosition);
            } catch (Exception e) {
                log.error("Subscriber failed to handle published event.", e);
            }
        }
    }

    public static void publish(SimSolenoidPort solenoid) {
        for (var event : solenoidSubscribers) {
            try {
                event.accept(solenoid);
            } catch (Exception e) {
                log.error("Subscriber failed to handle published event.", e);
            }
        }
    }

}
