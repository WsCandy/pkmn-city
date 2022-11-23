package com.gocity.graphql.move;

import com.gocity.graphql.pokemon.Pokemon;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
public class MoveService {

    public Future<List<Move>> getMoves(Pokemon pokemon) {
        return CompletableFuture.supplyAsync(() -> List.of(
            new Move(1, "Leech Seed")
        ));
    }

    public Future<List<Move>> getMoves(Pokemon pokemon, int delay) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(delay);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            return List.of(
                new Move(1, "Leech Seed")
            );
        });
    }
}
