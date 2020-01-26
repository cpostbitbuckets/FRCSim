package frc.robot.simulator.network.services.streams;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple stream observer that logs errors
 * @param <T>
 */
public abstract class LoggingStreamObserver<T> implements StreamObserver<T> {
    private static final Logger log = LoggerFactory.getLogger(LoggingStreamObserver.class);

    protected final String type;

    protected LoggingStreamObserver(String type) {
        this.type = type;
    }

    @Override
    public void onError(Throwable t) {
        log.error(type + " had error in connection: ", t);
    }

    @Override
    public void onCompleted() {
        log.error(type + " completed");
    }
}
