package com.gocity.graphql.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.graphql.support.ResourceDocumentSource;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;

@ActiveProfiles("test")
@GraphQlTest(includeFilters = @ComponentScan.Filter(Service.class))
public class IntegrationTest {

    protected final GraphQlTester tester;

    @Autowired
    public IntegrationTest(GraphQlTester tester) {
        List<Resource> locations = Collections.singletonList(
            new ClassPathResource("gql/")
        );

        var store = new ResourceDocumentSource(locations, ResourceDocumentSource.FILE_EXTENSIONS);

        this.tester = tester.mutate()
            .documentSource(store)
            .build();
    }
}
