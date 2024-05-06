package com.example.member.config;

import com.example.member.application.input_port.elastic.ElsFollowGrpcInputPort;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GrpcServerConfig {

    private final ElsFollowGrpcInputPort elsFollowGrpcInputPort;

    public void start() throws Exception {
        Server server = ServerBuilder.forPort(8084)
                .addService(elsFollowGrpcInputPort)
                .build();
        server.start();
        server.awaitTermination();
    }
}
