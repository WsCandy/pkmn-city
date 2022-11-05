package com.gocity.graphql.service;

import com.gocity.graphql.model.Pokemon;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class PokemonService {

    public CompletableFuture<Pokemon> findPokemon(int id, String name) {
        return CompletableFuture.supplyAsync(() -> new Pokemon(id, name, List.of()));
    }

    public CompletableFuture<Pokemon> findPokemon(int id, String name, int delay) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(delay);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            return new Pokemon(id, name, List.of());
        });
    }
}
