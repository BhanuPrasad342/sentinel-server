package com.sentineluba.server.alert.dto;

import java.time.LocalDateTime;

public class AlertResponse {

    private Long id;
    private Long userId;
    private int riskScore;
    private String riskLevel;
    private String message;
    private String status;
    private LocalDateTime createdAt;

    public AlertResponse() {
    }

    public AlertResponse(
            Long id,
            Long userId,
            int riskScore,
            String riskLevel,
            String message,
            String status,
            LocalDateTime createdAt) {

        this.id = id;
        this.userId = userId;
        this.riskScore = riskScore;
        this.riskLevel = riskLevel;
        this.message = message;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public int getRiskScore() {
        return riskScore;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}