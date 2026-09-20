package com.sentineluba.server.agent.repository;

import com.sentineluba.server.agent.entity.AgentCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgentCredentialRepository
        extends JpaRepository<AgentCredential, Long> {

    Optional<AgentCredential> findByAgentKeyAndActiveTrue(
            String agentKey
    );

    Optional<AgentCredential> findByDevice_DeviceId(
            String deviceId
    );
}