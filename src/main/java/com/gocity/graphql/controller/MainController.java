package com.gocity.graphql.controller;

import com.gocity.graphql.model.Move;
import com.gocity.graphql.model.Pokemon;
import com.gocity.graphql.service.MoveService;
import com.gocity.graphql.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;

@Controller
public class MainController {

    private final PokemonService pokemonService;
    private final MoveService moveService;
    
    @Autowired
    MainController(PokemonService pokemonService, MoveService movesService) {
        this.pokemonService = pokemonService;
        this.moveService = movesService;
    }

    @QueryMapping
    public Future<Pokemon> pokemon(@Argument Optional<String> name, @Argument Optional<Integer> id) {
        return pokemonService.findPokemon(id.orElse(1), name.orElse("Squirtle"));
    }

    @SchemaMapping
    public Future<List<Move>> moves(Pokemon pokemon) {
        return moveService.getMoves(pokemon, 1000);
    }

}
