package com.gocity.pokemon;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.proto.pokemon.Pokemon;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class PokemonRepository {

    private final List<PokemonDAO> pkmn;

    PokemonRepository(@Value("classpath:data/pokemon.json") Resource data) throws IOException {
        var mapper = new ObjectMapper();
        var file = data.getFile();
        var test = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

        pkmn = mapper.readValue(test, mapper.getTypeFactory().constructCollectionType(List.class, PokemonDAO.class));

        log.debug("Loaded Pokémon data in to memory: {}", pkmn);
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

        return Optional.empty();
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
