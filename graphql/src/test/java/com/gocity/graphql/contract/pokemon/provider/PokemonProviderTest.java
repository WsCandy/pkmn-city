package com.gocity.graphql.contract.pokemon.provider;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import com.gocity.graphql.GraphqlApplication;
import com.gocity.graphql.move.Move;
import com.gocity.graphql.move.MoveService;
import com.gocity.graphql.pokemon.Pokemon;
import com.gocity.graphql.pokemon.PokemonService;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@PactFolder("pacts")
@ActiveProfiles("test")
@Provider("pkmn-graphql")
@SpringBootTest(classes = GraphqlApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PokemonProviderTest {

    @MockBean
    private PokemonService pokemonService;

    @MockBean
    private MoveService moveService;

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    public void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("there is a Pokemon with moves in the database")
    public void setupPokemonFoundState() {
        var pkmn = Pokemon.builder()
            .id(1)
            .name("Bulbasaur")
            .build();

        var moves = List.of(
            new Move(1, "Leech Seed")
        );

        when(pokemonService.findPokemon(anyInt(), anyString()))
            .thenReturn(CompletableFuture.completedFuture(pkmn));

        when(moveService.getMoves(any(Pokemon.class), anyInt()))
            .thenReturn(CompletableFuture.completedFuture(moves));
    }
}
