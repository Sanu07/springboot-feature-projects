package com.test.graphql.resolver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FooDataFetcher {
    @GetMapping("excel")
    public void foo() {
        createInsertStatements();
    }

    private void createInsertStatements() {

    }
}