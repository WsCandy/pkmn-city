package com.gocity.graphql.model;

import java.util.List;

public record Pokemon(int id, String name, List<Move> moves) {
}
