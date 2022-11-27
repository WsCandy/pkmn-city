package com.gocity.graphql.util;

import io.grpc.BindableService;
import io.grpc.ManagedChannel;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import io.grpc.util.MutableHandlerRegistry;

public class MockGrpcServer {
    private final MutableHandlerRegistry serviceRegistry = new MutableHandlerRegistry();
    private final ManagedChannel channel;

    public MockGrpcServer(GrpcCleanupRule grpc) throws Exception {
        var name = InProcessServerBuilder.generateName();

        grpc.register(InProcessServerBuilder.forName(name)
            .fallbackHandlerRegistry(serviceRegistry)
            .directExecutor()
            .build()
            .start()
        );

        this.channel = grpc.register(
            InProcessChannelBuilder.forName(name)
                .directExecutor()
                .build()
        );
    }

    public ManagedChannel getChannel() {
        return this.channel;
    }

    public <T extends BindableService> void addService(T service) {
        serviceRegistry.addService(service);
    }
}
