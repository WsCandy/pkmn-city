package com.gocity.graphql.util;

public class ResourceMapping {

    private String name;
    private String contents;

    public ResourceMapping(String name, String contents) {
        this.name = name;
        this.contents = contents;
    }

    public String getName() {
        return name;
    }

    public String getContents() {
        return contents;
    }
}
