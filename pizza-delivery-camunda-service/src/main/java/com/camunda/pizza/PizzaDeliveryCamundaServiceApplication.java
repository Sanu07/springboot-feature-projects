package com.camunda.pizza;

import io.camunda.zeebe.spring.client.EnableZeebeClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableZeebeClient
public class PizzaDeliveryCamundaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PizzaDeliveryCamundaServiceApplication.class, args);
	}

}
