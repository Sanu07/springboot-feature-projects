package com.gateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfiguration {

	@Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
        		.route(r -> r.path("/consumers/**")
                        .uri("lb://consumer-service"))
        		.route(r -> r.path("/vendors/**")
                        .uri("lb://vendor-service"))
        		.route(r -> r.path("/admins/**")
                        .uri("lb://admin-service"))
                .build();
    }
}
