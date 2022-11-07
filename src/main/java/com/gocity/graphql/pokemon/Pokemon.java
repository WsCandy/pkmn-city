package com.gocity.graphql.pokemon;

import com.gocity.graphql.move.Move;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Pokemon {
    private int id;
    private String name;
    private List<Move> moves;
}
