package com.gocity.graphql.contract.pokemon.consumer;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactBuilder;
import au.com.dius.pact.consumer.junit.MockServerConfig;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.junit5.ProviderType;
import au.com.dius.pact.consumer.model.MockServerImplementation;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.V4Interaction;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.google.protobuf.InvalidProtocolBufferException;
import common.proto.pokemon.Pokemon;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static au.com.dius.pact.consumer.dsl.PactBuilder.filePath;
import static common.proto.pokemon.PokemonServiceGrpc.newBlockingStub;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ActiveProfiles("test")
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "pkmn-service", providerType = ProviderType.SYNCH_MESSAGE, pactVersion = PactSpecVersion.V4)
public class PokemonConsumerTest {

    // Needs plugin to work https://docs.pact.io/implementation_guides/pact_plugins
    // https://github.com/pact-foundation/pact-plugins/tree/main/examples/gRPC/area_calculator/consumer-jvm

    @Disabled
    @Pact(consumer = "pkmn-graphql")
    public V4Pact getPokemon(PactBuilder builder) {
        return builder
            .usingPlugin("protobuf")
            .expectsToReceive("a Pokémon", "core/interaction/synchronous-message")
            .with(Map.of(
                "pact:proto", filePath("../proto/src/test_proto3_optional/pokemon.proto"),
                "pact:content-type", "application/grpc",
                "pact:proto-service", "PokemonService/find",
                "request", Map.of(
                    "id", "matching(number, 1)",
                    "name", "matching(string, Bulbasaur)"
                ),
                "response", Map.of(
                    "id", "matching(number, 1)",
                    "name", "matching(string, Bulbasaur)"
                ))
            )
            .toPact();
    }

    @Test
    @Disabled
    @PactTestFor(pactMethod = "getPokemon")
    @MockServerConfig(implementation = MockServerImplementation.Plugin, registryEntry = "protobuf/transport/grpc")
    void getPokemon(MockServer mockServer, V4Interaction.SynchronousMessages interaction) throws InvalidProtocolBufferException {
        var channel = ManagedChannelBuilder.forTarget("[::1]:" + mockServer.getPort())
            .usePlaintext()
            .build();

        var stub = newBlockingStub(channel);
        var request = Pokemon.PokemonRequest.parseFrom(interaction.getRequest().getContents().getValue());
        var response = stub.find(request);

        assertThat(response.getName(), is("Bulbasaur"));
    }
}
