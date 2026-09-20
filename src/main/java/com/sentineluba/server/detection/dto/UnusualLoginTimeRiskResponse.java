package com.sentineluba.server.detection.dto;

import java.time.LocalDateTime;

public class UnusualLoginTimeRiskResponse {

    private Long userId;
    private LocalDateTime loginTime;
    private boolean unusual;
    private int riskScore;
    private String message;
    private LocalDateTime checkedAt;

    public UnusualLoginTimeRiskResponse() {
    }

    public UnusualLoginTimeRiskResponse(
            Long userId,
            LocalDateTime loginTime,
            boolean unusual,
            int riskScore,
            String message,
            LocalDateTime checkedAt) {

        this.userId = userId;
        this.loginTime = loginTime;
        this.unusual = unusual;
        this.riskScore = riskScore;
        this.message = message;
        this.checkedAt = checkedAt;
    }

    public Long getUserId() {
        return userId;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
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