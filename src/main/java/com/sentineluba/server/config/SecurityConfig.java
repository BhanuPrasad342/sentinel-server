package com.sentineluba.server.config;

import com.sentineluba.server.agent.filter.AgentAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final AgentAuthenticationFilter agentAuthenticationFilter;

    public SecurityConfig(
            AgentAuthenticationFilter agentAuthenticationFilter) {

        this.agentAuthenticationFilter = agentAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                // Enable CORS using CorsConfig
                .cors(cors -> {})

                .addFilterBefore(
                        agentAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                .authorizeHttpRequests(auth -> auth

                        // =====================================
                        // CORS PREFLIGHT
                        // =====================================

                        .requestMatchers(
                                HttpMethod.OPTIONS,
                                "/**"
                        ).permitAll()

                        // =====================================
                        // PUBLIC ENDPOINTS
                        // =====================================

                        .requestMatchers(
                                "/api/users/employee/**"
                        ).permitAll()

                        /*
                         * Temporary WebSocket access.
                         * We will secure the WebSocket handshake
                         * separately before deployment.
                         */
                        .requestMatchers(
                                "/ws/**"
                        ).permitAll()

                        // =====================================
                        // AGENT
                        // =====================================

                        /*
                         * AgentAuthenticationFilter validates
                         * X-Agent-Key for POST activity events.
                         */
                        .requestMatchers(
                                "/api/activity-events"
                        ).authenticated()

                        // =====================================
                        // MANAGER
                        // =====================================

                        .requestMatchers(
                                "/api/alerts/**",
                                "/api/manager/**"
                        ).hasRole("MANAGER")

                        // =====================================
                        // EVERYTHING ELSE
                        // =====================================

                        .anyRequest().authenticated()
                )

                .httpBasic(httpBasic -> {});

        return http.build();
    }
}