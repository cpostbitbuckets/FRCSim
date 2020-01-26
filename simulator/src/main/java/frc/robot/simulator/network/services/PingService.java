package frc.robot.simulator.network.services;

import frc.robot.simulator.network.proto.PingProto;
import frc.robot.simulator.network.proto.PingServiceGrpc;
import io.grpc.stub.StreamObserver;

public class PingService extends PingServiceGrpc.PingServiceImplBase {
    @Override
    public void ping(PingProto.PingMessage request, StreamObserver<PingProto.PongResponse> responseObserver) {
        responseObserver.onNext(PingProto.PongResponse.getDefaultInstance());
        responseObserver.onCompleted();
    }

}
