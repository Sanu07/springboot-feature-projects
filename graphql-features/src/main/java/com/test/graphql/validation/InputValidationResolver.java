package com.test.graphql.validation;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.test.graphql.generated.DgsConstants;
import com.test.graphql.generated.types.Book;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@DgsComponent
@Validated
public class InputValidationResolver {

    @DgsQuery(field = DgsConstants.QUERY.BookInputValidator)
    public Book bookValidation(@InputArgument("input") @Valid BookInput bookInput) {
        return Book.newBuilder().id(1).name("book-1").build();
    }
}
