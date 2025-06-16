package com.sarasavi.lib_api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LibApiGatewayApplication {

    public static void main(String[] args) {

        SpringApplication.run(LibApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Book Service Route
                .route(r -> r.path("/api/v1/books/**")
                        .filters(f -> f
                                .addResponseHeader("X-Response-Header", "LibApiGateway")
                        )
                        .uri("http://localhost:8081")
                )
                .build();
    }
}
