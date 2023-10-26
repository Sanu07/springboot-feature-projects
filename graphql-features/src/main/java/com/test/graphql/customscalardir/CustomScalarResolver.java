package com.test.graphql.customscalardir;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.test.graphql.generated.DgsConstants;
import com.test.graphql.generated.types.Book;

@DgsComponent
public class CustomScalarResolver {

    @DgsQuery(field = DgsConstants.QUERY.Book)
    public Book getDetails() {
        return Book.newBuilder()
                .id(1)
                .name("book-1")
                .isbn(ISBN.builder().isbn("1234567890").build())
                .build();
    }

    @DgsQuery(field = DgsConstants.QUERY.BookWithInvalidId)
    public Book getDetailsWithInvalidId() {
        return Book.newBuilder()
                .id(-1)
                .name("book-1")
                .isbn(ISBN.builder().isbn("1234567890").build())
                .build();
    }

    @DgsQuery(field = DgsConstants.QUERY.BookWithInvalidISBN)
    public Book getDetailsWithInvalidISBN() {
        return Book.newBuilder()
                .id(1)
                .name("book-1")
                .isbn(ISBN.builder().isbn("12345").build())
                .build();
    }
}
