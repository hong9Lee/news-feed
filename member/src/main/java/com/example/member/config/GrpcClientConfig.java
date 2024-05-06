package com.example.member.config;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import member.MemberServiceGrpc;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {

    @Bean
    @Qualifier("managedFeedChannel")
    public ManagedChannel managedFeedChannel() {
        return ManagedChannelBuilder.forAddress("localhost", 8083)
                .usePlaintext()
                .build();
    }

    @Bean
    @Qualifier("feedServiceStub")
    public MemberServiceGrpc.MemberServiceBlockingStub feedServiceStub(@Qualifier("managedFeedChannel") ManagedChannel channel) {
        return MemberServiceGrpc.newBlockingStub(channel);
    }
}
