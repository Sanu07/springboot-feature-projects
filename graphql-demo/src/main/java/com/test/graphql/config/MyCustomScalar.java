package com.test.graphql.config;

import com.netflix.graphql.dgs.DgsScalar;
import com.test.graphql.model.MyCustom;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

@DgsScalar(name = "MyScalar")
public class MyCustomScalar implements Coercing<MyCustom, String> {
  @Override
  public String serialize(@NotNull Object dataFetcherResult) throws CoercingSerializeException {
    if (dataFetcherResult instanceof MyCustom) {
      return ((MyCustom) dataFetcherResult).toString();
    }
    throw new CoercingSerializeException("Not a valid error");
  }

  @Override
  public MyCustom parseValue(@NotNull Object input) throws CoercingParseValueException {
    return MyCustom.builder().name("name-test").value(11).build();
  }

  @Override
  public MyCustom parseLiteral(@NotNull Object input) throws CoercingParseLiteralException {
    if (!(input instanceof StringValue)) {
      throw new CoercingParseLiteralException("error");
    }
    if (StringUtils.isEmpty(((StringValue) input).getValue())) return null;
    else
      return MyCustom
              .builder()
              .name(((StringValue) input).getValue())
              .build();
  }
}
