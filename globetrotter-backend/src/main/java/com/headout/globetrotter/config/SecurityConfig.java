package com.headout.globetrotter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //Disable CSRF for stateless APIs
                .csrf(csrf -> csrf.disable())
                //Enable default CORS settings
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        //Allow public access to API endpoints and Swagger docs
                        .requestMatchers("/api/**","/swagger-ui/**","/api-docs/**").permitAll()
                        //Secure all other endpoints
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}
