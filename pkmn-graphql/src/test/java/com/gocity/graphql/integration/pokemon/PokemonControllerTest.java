package com.gocity.graphql.integration.pokemon;

import com.gocity.graphql.integration.pokemon.mock.PokemonServiceFindMock;
import com.gocity.graphql.pokemon.PokemonClient;
import com.gocity.graphql.util.ResourceLoader;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import io.grpc.util.MutableHandlerRegistry;
import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

@ActiveProfiles("test")
@GraphQlTest(includeFilters = {@ComponentScan.Filter(Service.class), @ComponentScan.Filter(Configuration.class)})
class PokemonControllerTest {

    @Rule
    public final GrpcCleanupRule grpc = new GrpcCleanupRule();

    private final GraphQlTester tester;
    private final PokemonClient client;
    private final MutableHandlerRegistry serviceRegistry = new MutableHandlerRegistry();

    @Autowired
    public PokemonControllerTest(GraphQlTester tester, PokemonClient client) {
        this.tester = tester;
        this.client = client;
    }

    @BeforeEach
    public void setup() throws Exception {
        var name = InProcessServerBuilder.generateName();

        grpc.register(InProcessServerBuilder.forName(name)
            .fallbackHandlerRegistry(serviceRegistry)
            .directExecutor()
            .build()
            .start()
        );

        var channel = grpc.register(
            InProcessChannelBuilder.forName(name)
                .directExecutor()
                .build()
        );

        client.setStub(channel);
    }

    private static Stream<Arguments> pokemonResponseProvider() throws Exception {
        var queries = ResourceLoader.resourceMap("classpath:integration/pokemon/*.graphql");
        var responses = ResourceLoader.resourceMap("classpath:integration/pokemon/*.json");

        return queries.entrySet().stream()
            .map(e -> Arguments.of(Named.of(e.getValue().getName(), e.getValue()), responses.get(e.getKey())));
    }

    @MethodSource("pokemonResponseProvider")
    @ParameterizedTest(name = "should return the correct response values for {0} query")
    void shouldReturnCorrectPokemonResponse(File query, File response) throws Exception {
        var document = FileUtils.readFileToString(query, StandardCharsets.UTF_8);
        var expected = FileUtils.readFileToString(response, StandardCharsets.UTF_8);

        serviceRegistry.addService(new PokemonServiceFindMock(expected));

        tester.document(document)
            .execute()
            .path("pokemon")
            .matchesJson(expected);
    }
}
