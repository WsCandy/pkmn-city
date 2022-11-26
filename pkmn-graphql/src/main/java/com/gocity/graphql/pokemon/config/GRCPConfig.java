package com.gocity.graphql.pokemon.config;

import io.grpc.Channel;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GRCPConfig {

    @Bean(name = "pkmn-channel")
    Channel pokemonChannel() {
        return Grpc.newChannelBuilder("localhost:8081", InsecureChannelCredentials.create())
            .build();
    }
}
