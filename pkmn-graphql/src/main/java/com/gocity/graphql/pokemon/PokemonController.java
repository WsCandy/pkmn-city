package com.gocity.graphql.pokemon;

import common.proto.pokemon.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;
import java.util.concurrent.Future;

@Controller
public class PokemonController {

    private final PokemonClient pokemonService;

    @Autowired
    PokemonController(PokemonClient pokemonClient) {
        this.pokemonService = pokemonClient;
    }

    @QueryMapping
    public Future<Pokemon.PokemonResponse> pokemon(@Argument Optional<String> name, @Argument Optional<Integer> id) {
        return pokemonService.findPokemon(id, name);
    }
}
