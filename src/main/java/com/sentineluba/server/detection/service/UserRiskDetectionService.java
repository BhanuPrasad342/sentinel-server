package com.sentineluba.server.detection.service;

import com.sentineluba.server.detection.dto.DownloadRiskResponse;
import com.sentineluba.server.detection.dto.SensitiveFileRiskResponse;
import com.sentineluba.server.detection.dto.UnusualIpRiskResponse;
import com.sentineluba.server.detection.dto.UnusualLoginTimeRiskResponse;
import com.sentineluba.server.detection.dto.FileSharingRiskResponse;
import com.sentineluba.server.detection.dto.UserRiskResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserRiskDetectionService {

    private final DownloadDetectionService downloadDetectionService;
    private final SensitiveFileDetectionService sensitiveFileDetectionService;
    private final UnusualIpDetectionService unusualIpDetectionService;
    private final UnusualLoginTimeDetectionService unusualLoginTimeDetectionService;
    private final FileSharingDetectionService fileSharingDetectionService;

    public UserRiskDetectionService(
            DownloadDetectionService downloadDetectionService,
            SensitiveFileDetectionService sensitiveFileDetectionService,
            UnusualIpDetectionService unusualIpDetectionService,
            UnusualLoginTimeDetectionService unusualLoginTimeDetectionService,
            FileSharingDetectionService fileSharingDetectionService) {

        this.downloadDetectionService = downloadDetectionService;
        this.sensitiveFileDetectionService = sensitiveFileDetectionService;
        this.unusualIpDetectionService = unusualIpDetectionService;
        this.unusualLoginTimeDetectionService = unusualLoginTimeDetectionService;
        this.fileSharingDetectionService = fileSharingDetectionService;
    }

    public UserRiskResponse calculateUserRisk(Long userId) {

        DownloadRiskResponse downloadRisk =
                downloadDetectionService.checkDownloadRisk(userId);

        SensitiveFileRiskResponse sensitiveFileRisk =
                sensitiveFileDetectionService.checkSensitiveFileRisk(userId);

        UnusualIpRiskResponse unusualIpRisk =
                unusualIpDetectionService.checkUnusualIpRisk(userId);

        UnusualLoginTimeRiskResponse loginTimeRisk =
                unusualLoginTimeDetectionService.checkLoginTimeRisk(userId);

        FileSharingRiskResponse fileSharingRisk =
                fileSharingDetectionService.checkFileSharingRisk(userId);

        int overallRisk =
                downloadRisk.getRiskScore()
                        + sensitiveFileRisk.getRiskScore()
                        + unusualIpRisk.getRiskScore()
                        + loginTimeRisk.getRiskScore()
                        + fileSharingRisk.getRiskScore();

        if (overallRisk > 100) {
            overallRisk = 100;
        }

        String riskLevel = determineRiskLevel(overallRisk);

        String message = buildMessage(
                downloadRisk,
                sensitiveFileRisk,
                unusualIpRisk,
                loginTimeRisk,
                fileSharingRisk
        );

        return new UserRiskResponse(
                userId,
                downloadRisk.getRiskScore(),
                sensitiveFileRisk.getRiskScore(),
                overallRisk,
                riskLevel,
                message,
                LocalDateTime.now()
        );
    }

    private String determineRiskLevel(int riskScore) {

        if (riskScore >= 80) {
            return "CRITICAL";
        }

        if (riskScore >= 60) {
            return "HIGH";
        }

        if (riskScore >= 30) {
            return "MEDIUM";
        }

        return "LOW";
    }

    private String buildMessage(
            DownloadRiskResponse downloadRisk,
            SensitiveFileRiskResponse sensitiveFileRisk,
            UnusualIpRiskResponse unusualIpRisk,
            UnusualLoginTimeRiskResponse loginTimeRisk,
            FileSharingRiskResponse fileSharingRisk) {

        int triggeredRules = 0;

        if (downloadRisk.isSuspicious()) {
            triggeredRules++;
        }

        if (sensitiveFileRisk.isSensitive()) {
            triggeredRules++;
        }

        if (unusualIpRisk.isUnusual()) {
            triggeredRules++;
        }

        if (loginTimeRisk.isUnusual()) {
            triggeredRules++;
        }

        if (fileSharingRisk.isSuspicious()) {
            triggeredRules++;
        }

        if (triggeredRules >= 2) {
            return "Multiple suspicious behaviors detected";
        }

        if (downloadRisk.isSuspicious()) {
            return "Excessive download activity detected";
        }

        if (sensitiveFileRisk.isSensitive()) {
            return "Sensitive file access detected";
        }

        if (unusualIpRisk.isUnusual()) {
            return "Unusual IP address detected";
        }

        if (loginTimeRisk.isUnusual()) {
            return "Unusual login time detected";
        }

        if (fileSharingRisk.isSuspicious()) {
            return "File sharing or external transfer detected";
        }

        return "User activity is normal";
    }
}