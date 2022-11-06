package com.gocity.graphql.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ResourceLoader {

    private static File loadFile(Resource resource) {
        try {
            return resource.getFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getName(File file) {
        return file.getName().split("\\.")[0];
    }

    public static Map<String, File> resourceMap(String pattern) throws Exception {
        var loader = new PathMatchingResourcePatternResolver();

        return Arrays.stream(loader.getResources(pattern))
            .map(ResourceLoader::loadFile)
            .collect(Collectors.toMap(ResourceLoader::getName, Function.identity()));
    }
}
