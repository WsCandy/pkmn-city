package com.gocity.pokemon;

import city.pkmn.proto.pokemon.Pokemon;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("pokemon")
public class PokemonDAO {
    int id;
    String name;
    String species;
    String description;

    public Pokemon.PokemonResponse toResponse() {
        return Pokemon.PokemonResponse.newBuilder()
            .setId(id)
            .setName(name)
            .setSpecies(species)
            .setDescription(description)
            .build();
    }
}
