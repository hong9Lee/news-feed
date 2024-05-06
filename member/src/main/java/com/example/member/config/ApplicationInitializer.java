package com.example.member.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationInitializer implements ApplicationRunner {

    private final GrpcServerConfig grpcServerConfig;

    public ApplicationInitializer(GrpcServerConfig grpcServerConfig) {
        this.grpcServerConfig = grpcServerConfig;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        grpcServerConfig.start();
    }
}

