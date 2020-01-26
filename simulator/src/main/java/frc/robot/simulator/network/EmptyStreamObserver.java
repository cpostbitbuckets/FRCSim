package frc.robot.simulator.network;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmptyStreamObserver implements StreamObserver<Empty> {
    private static final Logger log = LoggerFactory.getLogger(EmptyStreamObserver.class);

    @Override
    public void onNext(Empty value) {
    }

    @Override
    public void onError(Throwable t) {
        // lost connection
        log.error("Lost connection to server: ", t);
    }

    @Override
    public void onCompleted() {
    }
}
