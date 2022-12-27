package com.gocity.pokemon;

import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class PokemonServer {

    @Autowired
    public PokemonServer(PokemonService service) throws IOException, InterruptedException {
        var server = Grpc.newServerBuilderForPort(8081, InsecureServerCredentials.create())
                .addService(service)
                .build();

        log.debug("Starting gRPC Server on port {}", 8081);
        
        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.debug("JVM shut down... shutting down...");
            try {
                server.shutdown()
                        .awaitTermination(10L, TimeUnit.SECONDS);

            } catch (InterruptedException e) {
                log.error("There was a problem shutting down the server");
            }

            log.info("Server shut down gracefully");
        }));

        server.awaitTermination();
    }
}
