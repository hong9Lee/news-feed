package com.example.feed.config;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import member.MemberServiceGrpc;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {

    @Bean
    @Qualifier("managedMemberChannel")
    public ManagedChannel managedMemberChannel() {
        return ManagedChannelBuilder.forAddress("localhost", 8081)
                .usePlaintext()
                .build();
    }

    @Bean
    @Qualifier("memberServiceStub")
    public MemberServiceGrpc.MemberServiceBlockingStub memberServiceStub(@Qualifier("managedMemberChannel") ManagedChannel channel) {
        return MemberServiceGrpc.newBlockingStub(channel);
    }
}
