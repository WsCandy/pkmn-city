package com.gocity.graphql.pokemon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;
import java.util.concurrent.Future;

@Controller
public class PokemonController {

    private final PokemonService pokemonService;

    @Autowired
    PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @QueryMapping
    public Future<Pokemon> pokemon(@Argument Optional<String> name, @Argument Optional<Integer> id) {
        return pokemonService.findPokemon(id.orElse(1), name.orElse("Squirtle"));
    }
}
