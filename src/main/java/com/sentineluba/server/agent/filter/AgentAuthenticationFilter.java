package com.sentineluba.server.agent.filter;

import com.sentineluba.server.agent.entity.AgentCredential;
import com.sentineluba.server.agent.repository.AgentCredentialRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class AgentAuthenticationFilter extends OncePerRequestFilter {

    private final AgentCredentialRepository agentCredentialRepository;

    public AgentAuthenticationFilter(
            AgentCredentialRepository agentCredentialRepository) {

        this.agentCredentialRepository = agentCredentialRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // Only protect activity event ingestion
        if (!path.equals("/api/activity-events")
                || !"POST".equalsIgnoreCase(request.getMethod())) {

            filterChain.doFilter(request, response);
            return;
        }

        String agentKey = request.getHeader("X-Agent-Key");

        if (agentKey == null || agentKey.isBlank()) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");

            response.getWriter().write(
                    "{\"error\":\"Agent authentication required\"}"
            );

            return;
        }

        AgentCredential credential =
                agentCredentialRepository
                        .findByAgentKeyAndActiveTrue(agentKey)
                        .orElse(null);

        if (credential == null) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");

            response.getWriter().write(
                    "{\"error\":\"Invalid agent key\"}"
            );

            return;
        }

        // Credential → Device → Employee
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        credential.getDevice().getUser(),
                        null,
                        Collections.emptyList()
                );

        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}