package com.sentineluba.server.agent.repository;

import com.sentineluba.server.agent.entity.AgentDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AgentDeviceRepository
        extends JpaRepository<AgentDevice, Long> {

    Optional<AgentDevice> findByDeviceId(String deviceId);

    List<AgentDevice> findAllByOrderByIdDesc();
}