package com.influx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class InfluxDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfluxDemoApplication.class, args);
	}

}
