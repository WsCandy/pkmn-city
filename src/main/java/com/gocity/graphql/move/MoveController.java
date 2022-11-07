package com.gocity.graphql.move;

import com.gocity.graphql.pokemon.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.concurrent.Future;

@Controller
public class MoveController {
    private final MoveService moveService;

    @Autowired
    MoveController(MoveService movesService) {
        this.moveService = movesService;
    }

    @SchemaMapping
    public Future<List<Move>> moves(Pokemon pokemon) {
        return moveService.getMoves(pokemon, 1000);
    }
}
