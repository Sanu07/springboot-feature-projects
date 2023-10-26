package com.test.graphql.customscalardir;

import com.netflix.graphql.dgs.DgsDirective;
import graphql.schema.*;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;

@DgsDirective(name = "uppercase")
public class UppercaseDirective implements SchemaDirectiveWiring {
    @Override
    public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> env) {
        GraphQLFieldsContainer fieldsContainer = env.getFieldsContainer();
        GraphQLFieldDefinition fieldDefinition = env.getFieldDefinition();

        DataFetcher<?> originalDataFetcher = env.getCodeRegistry().getDataFetcher(fieldsContainer, fieldDefinition);
        DataFetcher<?> dataFetcher = DataFetcherFactories.wrapDataFetcher(
                originalDataFetcher,
                (dataFetchingEnvironment, value) -> {
                    if (value instanceof String) {
                        return ((String) value).toUpperCase();
                    }
                    return value;
                }
        );
        env.getCodeRegistry().dataFetcher(fieldsContainer, fieldDefinition, dataFetcher);
        return fieldDefinition;
    }
}