package com.gocity.graphql.pokemon;

import com.gocity.graphql.pokemon.mock.PokemonServiceFindMock;
import com.gocity.graphql.util.MockGrpcServer;
import com.gocity.graphql.util.ResourceLoader;
import io.grpc.testing.GrpcCleanupRule;
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
class PokemonIntegrationTest {

    @Rule
    public final GrpcCleanupRule grpc = new GrpcCleanupRule();

    private MockGrpcServer server;
    private final GraphQlTester tester;
    private final PokemonClient client;

    @Autowired
    public PokemonIntegrationTest(GraphQlTester tester, PokemonClient client) {
        this.tester = tester;
        this.client = client;
    }

    @BeforeEach
    public void setup() throws Exception {
        server = new MockGrpcServer(grpc);
        client.setStub(server.getChannel());
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

        server.addService(new PokemonServiceFindMock(expected));

        tester.document(document)
            .execute()
            .path("pokemon")
            .matchesJson(expected);
    }
}
