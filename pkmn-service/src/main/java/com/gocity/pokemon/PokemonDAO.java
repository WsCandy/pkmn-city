package com.gocity.pokemon;

import common.proto.pokemon.Pokemon;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PokemonDAO {
    private int id;
    private String name;
    private String species;
    private String description;

    // Needed for mapping, can be deleted when it's an actual DB.
    public PokemonDAO() {
    }

    public Pokemon.PokemonResponse toResponse() {
        return Pokemon.PokemonResponse.newBuilder()
            .setId(id)
            .setName(name)
            .setSpecies(species)
            .setDescription(description)
            .build();
    }
}
