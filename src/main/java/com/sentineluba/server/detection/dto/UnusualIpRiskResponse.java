package com.sentineluba.server.detection.dto;

import java.time.LocalDateTime;

public class UnusualIpRiskResponse {

    private Long userId;
    private String ipAddress;
    private boolean unusual;
    private int riskScore;
    private String message;
    private LocalDateTime checkedAt;

    public UnusualIpRiskResponse() {
    }

    public UnusualIpRiskResponse(
            Long userId,
            String ipAddress,
            boolean unusual,
            int riskScore,
            String message,
            LocalDateTime checkedAt) {

        this.userId = userId;
        this.ipAddress = ipAddress;
        this.unusual = unusual;
        this.riskScore = riskScore;
        this.message = message;
        this.checkedAt = checkedAt;
    }

    public Long getUserId() {
        return userId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public boolean isUnusual() {
        return unusual;
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