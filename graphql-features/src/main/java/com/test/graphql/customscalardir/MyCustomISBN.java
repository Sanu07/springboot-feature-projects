package com.test.graphql.customscalardir;

import com.netflix.graphql.dgs.DgsScalar;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import org.jetbrains.annotations.NotNull;

@DgsScalar(name = "ISBN")
public class MyCustomISBN implements Coercing<ISBN, String> {
  @Override
  public String serialize(@NotNull Object dataFetcherResult) throws CoercingSerializeException {
    if (dataFetcherResult instanceof ISBN) {
      final String isbn = ((ISBN) dataFetcherResult).getIsbn();
      if (isbn != null && isbn.length() == 10) {
        return isbn;
      }
    }
    throw new CoercingSerializeException("Not a valid isbn");
  }

  @Override
  public @NotNull ISBN parseValue(@NotNull Object input) throws CoercingParseValueException {
    return ISBN.builder().isbn(input.toString()).build();
  }

  @Override
  public @NotNull ISBN parseLiteral(@NotNull Object input) throws CoercingParseLiteralException {
    if (input instanceof StringValue) {
      return ISBN.builder().isbn(input.toString()).build();
    }
    throw new CoercingParseLiteralException("Not a valid isbn");
  }
}