package com.gocity.graphql.service;

import com.gocity.graphql.model.Pokemon;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
public class PokemonService {

    public Future<Pokemon> findPokemon(int id, String name) {
        return CompletableFuture.supplyAsync(() -> Pokemon.builder()
            .id(id)
            .name(name)
            .build()
        );
    }

    public Future<Pokemon> findPokemon(int id, String name, int delay) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(delay);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            return Pokemon.builder()
                .id(id)
                .name(name)
                .build();
        });
    }
}
