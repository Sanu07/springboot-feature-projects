package com.test.graphql.interfacesandunions;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.test.graphql.generated.DgsConstants;
import com.test.graphql.generated.types.*;

import java.util.Arrays;
import java.util.List;

@DgsComponent
public class InterfacesAndUnionsResolver {

    @DgsQuery(field = DgsConstants.QUERY.Products)
    public List<Product> getProduct() {
        return Arrays.asList(
                Mobile.newBuilder().productId(1).model("M1").offer(Coupon.newBuilder().code("ABC10").build()).build(),
                Mobile.newBuilder().productId(2).model("M2").offer(Discount.newBuilder().percent(10).build()).build(),
                Shirt.newBuilder().productId(3).color("Black").build()
        );
    }
}
