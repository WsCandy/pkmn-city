package com.gocity.graphql.model;

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
