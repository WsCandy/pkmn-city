package com.gocity.graphql.contract.pokemon.consumer;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static au.com.dius.pact.consumer.dsl.LambdaDsl.newJsonBody;

// This file is an example of a consumer, it's likely we will not have Java <-> Java communication over GraphQL.
// JS Pact library has built in GraphQL functionality for consumers making it a lot less hacky (See QUERY -.-).
@ActiveProfiles("test")
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "pkmn-graphql", pactVersion = PactSpecVersion.V3)
@GraphQlTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PokemonConsumerTest {
    private static final String CONSUMER_NAME = "pkmn-consumer";

    // language=JSON
    private static final String QUERY = """
            {"query": "{ pokemon(name: \\"Bulbasaur\\", id: 1) { id name moves { id name } }}"}
        """;

    @Pact(consumer = CONSUMER_NAME)
    public RequestResponsePact getPokemon(PactDslWithProvider builder) {
        var body = newJsonBody(b -> {
            b.object("data", data -> {
                data.object("pokemon", pkmn -> {
                    pkmn.integerType("id");
                    pkmn.stringType("name");
                    pkmn.array("moves", moves -> {
                        moves.object(move -> {
                            move.integerType("id");
                            move.stringType("name");
                        });
                    });
                });
            });
        });

        return builder
            .given("there is a Pokemon with moves in the database")
            .uponReceiving("a graphql query")
            .path("/graphql")
            .method("POST")
            .body(QUERY)
            .willRespondWith()
            .status(200)
            .body(body.build())
            .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "getPokemon")
    void test(MockServer mockServer) {
        var client = WebTestClient.bindToServer()
            .baseUrl(mockServer.getUrl())
            .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .build();

        client.post()
            .uri("/graphql")
            .bodyValue(QUERY)
            .exchange()
            .expectStatus()
            .isEqualTo(HttpStatus.OK)
            .expectBody()
            .jsonPath("$.data.pokemon");
    }
}
