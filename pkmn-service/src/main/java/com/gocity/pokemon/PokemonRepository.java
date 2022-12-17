package com.gocity.pokemon;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.proto.pokemon.Pokemon;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
public class PokemonRepository {

    private final List<PokemonDAO> pkmn;

    PokemonRepository(@Value("classpath:data/pokemon.json") Resource data) throws IOException {
        var mapper = new ObjectMapper();
        var stream = data.getInputStream();
        var test = new String(stream.readAllBytes(), StandardCharsets.UTF_8);

        pkmn = mapper.readValue(test, mapper.getTypeFactory().constructCollectionType(List.class, PokemonDAO.class));
    }

    public Optional<PokemonDAO> findBy(Pokemon.PokemonRequest request) {
        if (request.hasId() && request.hasName()) {
            return findBy(request.getName(), request.getId());
        }

        if (!request.hasName() && request.hasId()) {
            return findBy(request.getId());
        }

        if (request.hasName()) {
            return findBy(request.getName());
        }

        return findRandom();
    }

    private Optional<PokemonDAO> findRandom() {
        var r = new Random();

        return pkmn.stream()
            .skip(r.nextInt(pkmn.size()))
            .findAny();
    }

    private Optional<PokemonDAO> findBy(int id) {
        return pkmn.stream()
            .filter(pokemon -> pokemon.getId() == id)
            .findFirst();
    }

    private Optional<PokemonDAO> findBy(String name) {
        return pkmn.stream()
            .filter(pokemon -> name.equals(pokemon.getName()))
            .findFirst();
    }

    private Optional<PokemonDAO> findBy(String name, int id) {
        return pkmn.stream()
            .filter(pokemon -> name.equals(pokemon.getName()) && pokemon.getId() == id)
            .findFirst();
    }
}
