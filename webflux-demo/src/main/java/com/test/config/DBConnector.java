package com.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;

import io.r2dbc.spi.ConnectionFactory;

@Component
public class DBConnector {

	@Autowired
	ConnectionFactory connectionFactory;
	
	@Bean
	public DatabaseClient createConnection() {
		return DatabaseClient.create(connectionFactory);
	}
}
