package com.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;

@Configuration
@EnableR2dbcRepositories
public class Config {

//	@Bean
//    @Override
//    public R2dbcCustomConversions r2dbcCustomConversions() {
//        List<Converter<?, ?>> converters = Arrays.asList(new ListToJsonString(), new JsonStringToList());
//        return new R2dbcCustomConversions(getStoreConversions(), converters);
//    }
	
	@Bean
	public ConnectionFactory connectionFactory() {
		return ConnectionFactories.get(ConnectionFactoryOptions.builder()
				   .option(ConnectionFactoryOptions.DRIVER, "postgresql")
				   .option(ConnectionFactoryOptions.HOST, "localhost")
				   .option(ConnectionFactoryOptions.PORT, 5432)  // optional, defaults to 5432
				   .option(ConnectionFactoryOptions.USER, "postgres")
				   .option(ConnectionFactoryOptions.PASSWORD, "root")
				   .option(ConnectionFactoryOptions.DATABASE, "test")  // optional
				   .build());
	}
}
