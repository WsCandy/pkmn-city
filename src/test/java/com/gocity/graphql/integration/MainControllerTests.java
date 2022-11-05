package com.gocity.graphql.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gocity.graphql.model.Pokemon;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

class MainControllerTests extends IntegrationTest {

    private final ObjectMapper mapper;

    @Autowired
    public MainControllerTests(GraphQlTester tester, ObjectMapper mapper) {
        super(tester);
        this.mapper = mapper;
    }

    private static Stream<Arguments> shapeProvider() {
        return Stream.of(
            Arguments.of("query/bulbasaur", "response/bulbasaur"),
            Arguments.of("query/charmander", "response/charmander"),
            Arguments.of("query/squirtle", "response/squirtle")
        );
    }

    @MethodSource("shapeProvider")
    @ParameterizedTest(name = "should return the correct response values for the {0}")
    void shouldReturnCorrectPokemonResponse(String queryPath, String responsePath) throws Exception {
        var response = new ClassPathResource("gql/%s.json".formatted(responsePath))
            .getFile();

        var expected = mapper.readValue(response, Pokemon.class);

        tester.documentName(queryPath)
            .execute()
            .path("pokemon")
            .entity(Pokemon.class)
            .satisfies(pokemon -> assertThat(expected, samePropertyValuesAs(pokemon)));
    }
}
