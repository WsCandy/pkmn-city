package com.gocity.graphql.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ResourceLoader {

    private static ResourceMapping loadFileContents(Resource resource) {
        try {
            var stream = resource.getInputStream();
            var name = trimName(resource.getFilename());
            return new ResourceMapping(name, new String(stream.readAllBytes(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String trimName(@Nullable String name) {
        if (Optional.ofNullable(name).isEmpty()) {
            return "";
        }

        return name.split("\\.")[0];
    }

    public static Map<String, String> resourceMap(String pattern) throws Exception {
        var loader = new PathMatchingResourcePatternResolver();

        return Arrays.stream(loader.getResources(pattern))
            .map(ResourceLoader::loadFileContents)
            .collect(Collectors.toMap(ResourceMapping::getName, ResourceMapping::getContents));
    }
}
