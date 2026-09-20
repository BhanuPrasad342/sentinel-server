package com.sentineluba.server.detection.dto;

import java.time.LocalDateTime;

public class SensitiveFileRiskResponse {

    private Long userId;
    private String resource;
    private boolean sensitive;
    private int riskScore;
    private String message;
    private LocalDateTime checkedAt;

    public SensitiveFileRiskResponse() {
    }

    public SensitiveFileRiskResponse(
            Long userId,
            String resource,
            boolean sensitive,
            int riskScore,
            String message,
            LocalDateTime checkedAt) {

        this.userId = userId;
        this.resource = resource;
        this.sensitive = sensitive;
        this.riskScore = riskScore;
        this.message = message;
        this.checkedAt = checkedAt;
    }

    public Long getUserId() {
        return userId;
    }

    public String getResource() {
        return resource;
    }

    public boolean isSensitive() {
        return sensitive;
    }

    public int getRiskScore() {
        return riskScore;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCheckedAt() {
        return checkedAt;
    }
}