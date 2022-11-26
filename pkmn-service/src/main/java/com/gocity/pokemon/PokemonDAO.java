package com.gocity.pokemon;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PokemonDAO {
    private int id;
    private String name;

    // Needed for mapping, can be deleted when it's an actual DB.
    public PokemonDAO() {}
}
