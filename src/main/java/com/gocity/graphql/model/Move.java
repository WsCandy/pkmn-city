package com.gocity.graphql.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Move {
    private int id;
    private String name;
}
