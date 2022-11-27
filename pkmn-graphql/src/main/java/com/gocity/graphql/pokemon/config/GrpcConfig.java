package com.gocity.graphql.pokemon.config;

import com.google.gson.Gson;
import io.grpc.Channel;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

@Slf4j
@Configuration
public class GrpcConfig {

    @Value("classpath:grpc_config.json")
    private Resource resource;

    @SuppressWarnings("unchecked")
    private static Map<String, ?> getConfig(Resource resource) {
        try {
            var file = resource.getFile();
            var string = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

            return new Gson()
                .fromJson(string, Map.class);

        } catch (Exception e) {
            return Collections.emptyMap();
        }
    }

    @Bean(name = "pkmn-channel")
    Channel pokemonChannel() {
        var config = GrpcConfig.getConfig(resource);

        log.debug("{}", config);

        return Grpc.newChannelBuilder("localhost:8081", InsecureChannelCredentials.create())
            .defaultServiceConfig(config)
            .enableRetry()
            .build();
    }
}
