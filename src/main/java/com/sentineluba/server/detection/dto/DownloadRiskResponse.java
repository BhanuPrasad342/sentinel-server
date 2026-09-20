package com.sentineluba.server.detection.dto;

import java.time.LocalDateTime;

public class DownloadRiskResponse {

    private Long userId;
    private long downloadCount;
    private int threshold;
    private int windowMinutes;
    private boolean suspicious;
    private int riskScore;
    private String message;
    private LocalDateTime checkedAt;

    public DownloadRiskResponse() {
    }

    public DownloadRiskResponse(
            Long userId,
            long downloadCount,
            int threshold,
            int windowMinutes,
            boolean suspicious,
            int riskScore,
            String message,
            LocalDateTime checkedAt) {

        this.userId = userId;
        this.downloadCount = downloadCount;
        this.threshold = threshold;
        this.windowMinutes = windowMinutes;
        this.suspicious = suspicious;
        this.riskScore = riskScore;
        this.message = message;
        this.checkedAt = checkedAt;
    }

    public Long getUserId() {
        return userId;
    }

    public long getDownloadCount() {
        return downloadCount;
    }

    public int getThreshold() {
        return threshold;
    }

    public int getWindowMinutes() {
        return windowMinutes;
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