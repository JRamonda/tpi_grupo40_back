package com.frc.utn.grupo40.ApiGateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtGrantedAuthoritiesConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class GWConfig {
    @Bean
    public RouteLocator configurarRutas(RouteLocatorBuilder builder, @Value("${api-gateway.estaciones-url}") String uriEstaciones,
                                        @Value("${api-gateway.alquileres-url}") String uriAlquileres){

        return builder.routes()
                .route(r -> r
                        .host("localhost:8080")
                        .and()
                        .path("/estaciones/**")
                        .filters(f -> f.rewritePath("/estaciones", "/api/estaciones"))
                        .uri(uriEstaciones)
                )
                .route(
                        r -> r
                                .host("localhost:8080")
                                .and()
                                .path("/alquileres/**")
                                .filters(f -> f.rewritePath("/alquileres", "/api/alquileres"))
                                .uri(uriAlquileres)
                )
                .build();
    }


    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws
            Exception {
        http.authorizeExchange(exchanges -> exchanges
                                .pathMatchers(HttpMethod.POST, "/estaciones")
                                .hasRole("ADMINISTRADOR")
                                .pathMatchers(HttpMethod.GET, "/estaciones")
                                .hasRole("CLIENTE")
                                .pathMatchers( "/estaciones/cercana")
                                .hasRole("CLIENTE")
                                .pathMatchers( HttpMethod.GET, "/alquileres")
                            .hasRole("ADMINISTRADOR")
                                // Cualquier otra petición...
                                .anyExchange()
                                .authenticated()
                ).oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(Customizer.withDefaults()))
                .csrf(csrf -> csrf.disable());
        return http.build();
    }
    @Bean
    public ReactiveJwtAuthenticationConverter jwtAuthenticationConverter() {
        var jwtAuthenticationConverter = new ReactiveJwtAuthenticationConverter();
        var grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        // Se especifica el nombre del claim a analizar
        grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
        // Se agrega este prefijo en la conversión por una convención de Spring
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        // Se asocia el conversor de Authorities al Bean que convierte el token
        // JWT a un objeto Authorization
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(
                new ReactiveJwtGrantedAuthoritiesConverterAdapter(grantedAuthoritiesConverter));
        return jwtAuthenticationConverter;
    }


}
