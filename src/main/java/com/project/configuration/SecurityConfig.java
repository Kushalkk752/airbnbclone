package com.project.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.AuthenticationFilter;

@Configuration
public class SecurityConfig {

    private JWTFilter jwtFilter;

    public SecurityConfig(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity
    ) throws Exception {
        httpSecurity.csrf().disable().cors().disable();
        httpSecurity.addFilterBefore(jwtFilter, AuthorizationFilter.class);
        httpSecurity.authorizeHttpRequests().requestMatchers("/api/v1/users/signup",
                        "/api/v1/users",
                        "/api/v1/users/login",
                        "/api/v1/users/signup-property-owner",
                        "/api/v1/city/addCity",
                        "/api/v1/city",
                        "/api/v1/city/{id}",
                        "/api/v1/Country/addCountry",
                        "/api/v1/Country",
                        "/api/v1/Country/{id}",
                        "/api/v1/property/addProperty",
                        "/api/v1/property",
                        "/api/v1/property/searchHotels",
                        "/api/v1/property/{id}",
                        "/api/v1/review/addReview",
                        "/api/v1/review/user/review").permitAll().requestMatchers("/api/v1/country").hasAnyRole("ADMIN","OWNER","USER")
                .anyRequest().authenticated();
        return httpSecurity.build();
    }
}
