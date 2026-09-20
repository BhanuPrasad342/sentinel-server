package com.sentineluba.server.detection.dto;

import java.time.LocalDateTime;

public class UserRiskResponse {

    private Long userId;
    private int downloadRisk;
    private int sensitiveFileRisk;
    private int overallRisk;
    private String riskLevel;
    private String message;
    private LocalDateTime checkedAt;

    public UserRiskResponse() {
    }

    public UserRiskResponse(
            Long userId,
            int downloadRisk,
            int sensitiveFileRisk,
            int overallRisk,
            String riskLevel,
            String message,
            LocalDateTime checkedAt) {

        this.userId = userId;
        this.downloadRisk = downloadRisk;
        this.sensitiveFileRisk = sensitiveFileRisk;
        this.overallRisk = overallRisk;
        this.riskLevel = riskLevel;
        this.message = message;
        this.checkedAt = checkedAt;
    }

    public Long getUserId() {
        return userId;
    }

    public int getDownloadRisk() {
        return downloadRisk;
    }

    public int getSensitiveFileRisk() {
        return sensitiveFileRisk;
    }

    public int getOverallRisk() {
        return overallRisk;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCheckedAt() {
        return checkedAt;
    }
}