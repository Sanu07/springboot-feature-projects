package com.test.graphql.validation;

import lombok.Data;

import javax.validation.constraints.Size;


@Data
public class BookInput {
    private Integer id;
    @Size(min = 2, max = 5)
    private String name;
}
