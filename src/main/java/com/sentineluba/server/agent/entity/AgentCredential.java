package com.sentineluba.server.agent.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "agent_credentials")
public class AgentCredential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String agentKey;

    @Column(nullable = false)
    private boolean active;

    @OneToOne
    @JoinColumn(name = "device_id", nullable = false, unique = true)
    private AgentDevice device;

    public AgentCredential() {
    }

    public Long getId() {
        return id;
    }

    public String getAgentKey() {
        return agentKey;
    }

    public void setAgentKey(String agentKey) {
        this.agentKey = agentKey;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public AgentDevice getDevice() {
        return device;
    }

    public void setDevice(AgentDevice device) {
        this.device = device;
    }
}