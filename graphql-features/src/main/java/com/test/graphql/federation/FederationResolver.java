package com.test.graphql.federation;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import com.test.graphql.generated.DgsConstants;
import com.test.graphql.generated.types.Folio;
import com.test.graphql.generated.types.User;
import lombok.extern.slf4j.Slf4j;

@DgsComponent
@Slf4j
public class FederationResolver {

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.Folio)
    public Folio getSingleUser(@InputArgument("id") Integer id) {
        return Folio.newBuilder()
                .id(1)
                .folioNumber("FOLIO-123")
                .build();
    }
}
