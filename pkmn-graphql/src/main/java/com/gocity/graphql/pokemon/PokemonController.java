package com.gocity.graphql.pokemon;

import common.proto.pokemon.Pokemon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;
import java.util.concurrent.Future;

@Slf4j
@Controller
public class PokemonController {

    private final PokemonClient pokemonClient;

    @Autowired
    PokemonController(PokemonClient pokemonClient) {
        this.pokemonClient = pokemonClient;
    }

    @QueryMapping
    public Future<Pokemon.PokemonResponse> pokemon(@Argument Optional<String> name, @Argument Optional<Integer> id) {
        return pokemonClient.findPokemon(id, name);
    }
}
