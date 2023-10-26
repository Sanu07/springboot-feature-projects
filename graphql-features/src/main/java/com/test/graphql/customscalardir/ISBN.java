package com.test.graphql.customscalardir;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ISBN {
    private String isbn;
}
