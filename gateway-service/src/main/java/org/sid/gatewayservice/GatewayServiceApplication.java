package org.sid.gatewayservice;

import com.netflix.discovery.DiscoveryClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@SpringBootApplication
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }


    //@Bean
    RouteLocator routeLocator (RouteLocatorBuilder builder) {
        return builder.routes()
                .route((r)->r.path("/customers/**").uri("lb://CUSTOMER-SERVICE"))
                .route((r)->r.path("/products/**").uri("lb://IVENTORY-SERVICE"))
                .build();
    }

    @Bean
    DiscoveryClientRouteDefinitionLocator  definitionLocator(
            ReactiveDiscoveryClient rdc,
            DiscoveryLocatorProperties properties) {
        return new DiscoveryClientRouteDefinitionLocator(rdc,properties);
    }


    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:64163"); // Your frontend's origin
        config.addAllowedMethod("*"); // Allow all HTTP methods
        config.addAllowedHeader("*"); // Allow all headers
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Apply to all routes

        return new CorsWebFilter(source);
    }
}
