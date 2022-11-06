package com.gocity.graphql.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gocity.graphql.model.Pokemon;
import com.gocity.graphql.util.ResourceLoader;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@ActiveProfiles("test")
@GraphQlTest(includeFilters = @ComponentScan.Filter(Service.class))
class MainControllerTests {

    private final ObjectMapper mapper;
    private final GraphQlTester tester;

    @Autowired
    public MainControllerTests(GraphQlTester tester, ObjectMapper mapper) {
        this.tester = tester;
        this.mapper = mapper;
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
        var expected = mapper.readValue(response, Pokemon.class);
        var document = FileUtils.readFileToString(query, StandardCharsets.UTF_8);

        tester.document(document)
            .execute()
            .path("pokemon")
            .entity(Pokemon.class)
            .satisfies(pokemon -> assertThat(expected, samePropertyValuesAs(pokemon)));
    }
}
