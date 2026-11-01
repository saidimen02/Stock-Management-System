
package com.stockmanagement.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth
            		.requestMatchers("/error").permitAll()
            	    .requestMatchers("/api/auth/login").permitAll()

            	    // Only managers can manage users
            	    .requestMatchers("/api/users/**").hasRole("MANAGER")

            	    // Both managers and cashiers can view products
            	    .requestMatchers(HttpMethod.GET, "/api/products/**")
            	        .hasAnyRole("MANAGER", "CASHIER")

            	    // Only managers can modify products
            	    .requestMatchers("/api/products/**").hasRole("MANAGER")

            	    // Both roles can view categories
            	    .requestMatchers(HttpMethod.GET, "/api/categories/**")
            	        .hasAnyRole("MANAGER", "CASHIER")

            	    // Only managers can modify categories
            	    .requestMatchers("/api/categories/**").hasRole("MANAGER")

            	    .requestMatchers(HttpMethod.GET, "/api/sales/**").hasRole("MANAGER")
            	    .requestMatchers(HttpMethod.POST, "/api/sales").hasAnyRole("MANAGER", "CASHIER")
            	    
            	   
            	    .requestMatchers(HttpMethod.GET, "/api/stock-movements/**")
            	    .hasRole("MANAGER")

            	.requestMatchers(HttpMethod.POST, "/api/stock-movements")
            	    .hasAnyRole("MANAGER", "CASHIER")
            	    
            	    .requestMatchers("/api/suppliers/**").hasRole("MANAGER")
            	    
            	    .requestMatchers("/api/low-stock/**").hasRole("MANAGER")
            	    .requestMatchers("/api/dashboard/**").hasRole("MANAGER")
            	    .anyRequest().authenticated()
            	)
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}
