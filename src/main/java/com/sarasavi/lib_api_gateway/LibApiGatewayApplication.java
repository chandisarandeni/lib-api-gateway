package com.sarasavi.lib_api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;

import java.util.List;

@SpringBootApplication
public class LibApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibApiGatewayApplication.class, args);
    }

    // ✅ CORS Web Filter Bean
    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173/", "http://localhost:5176"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.addAllowedHeader("*");
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        // ✅ Fix: return CorsWebFilter using CorsConfigurationSource interface
        return new CorsWebFilter((CorsConfigurationSource) source);
    }


    // ✅ Gateway Route Configuration
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()

                // Book Service Route
                .route(r -> r.path("/api/v1/books/**")
                        .filters(f -> f.addResponseHeader("X-Response-Header", "LibApiGateway"))
                        .uri("http://localhost:8083"))

                // Ratings Service Route
                .route(r -> r.path("/api/v1/ratings/**")
                        .filters(f -> f.addResponseHeader("X-Response-Header", "LibApiGateway"))
                        .uri("http://localhost:8086"))

                // Member Service Route
                .route(r -> r.path("/api/v1/members/**")
                        .filters(f -> f.addResponseHeader("X-Response-Header", "LibApiGateway"))
                        .uri("http://localhost:8082"))

                // Librarian Service Route
                .route(r -> r.path("/api/v1/librarians/**")
                        .filters(f -> f.addResponseHeader("X-Response-Header", "LibApiGateway"))
                        .uri("http://localhost:8082"))

                // Admin Service Route
                .route(r -> r.path("/api/v1/admins/**")
                        .filters(f -> f.addResponseHeader("X-Response-Header", "LibApiGateway"))
                        .uri("http://localhost:8082"))

                // Borrowing Service Route
                .route(r -> r.path("/api/v1/borrowings/**")
                        .filters(f -> f.addResponseHeader("X-Response-Header", "LibApiGateway"))
                        .uri("http://localhost:8084"))

                .build();
    }
}
