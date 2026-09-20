package com.sentineluba.server.detection.dto;

import java.time.LocalDateTime;

public class FileSharingRiskResponse {

    private Long userId;
    private String resource;
    private String action;
    private boolean suspicious;
    private int riskScore;
    private String message;
    private LocalDateTime checkedAt;

    public FileSharingRiskResponse() {
    }

    public FileSharingRiskResponse(
            Long userId,
            String resource,
            String action,
            boolean suspicious,
            int riskScore,
            String message,
            LocalDateTime checkedAt) {

        this.userId = userId;
        this.resource = resource;
        this.action = action;
        this.suspicious = suspicious;
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

    public String getAction() {
        return action;
    }

    public boolean isSuspicious() {
        return suspicious;
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