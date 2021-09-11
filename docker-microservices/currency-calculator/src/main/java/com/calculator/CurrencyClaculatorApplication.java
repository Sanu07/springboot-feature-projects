package com.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CurrencyClaculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyClaculatorApplication.class, args);
	}

}
