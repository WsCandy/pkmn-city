package com.gocity.graphql.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@GraphQlTest(includeFilters = @ComponentScan.Filter(Service.class))
public class IntegrationTest {

    protected final GraphQlTester tester;

    @Autowired
    public IntegrationTest(GraphQlTester tester) {
        this.tester = tester;
    }
}
