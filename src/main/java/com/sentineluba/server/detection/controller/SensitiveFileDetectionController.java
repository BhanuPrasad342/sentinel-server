package com.sentineluba.server.detection.controller;

import com.sentineluba.server.detection.dto.SensitiveFileRiskResponse;
import com.sentineluba.server.detection.service.SensitiveFileDetectionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/detection")
public class SensitiveFileDetectionController {

    private final SensitiveFileDetectionService sensitiveFileDetectionService;

    public SensitiveFileDetectionController(
            SensitiveFileDetectionService sensitiveFileDetectionService) {

        this.sensitiveFileDetectionService =
                sensitiveFileDetectionService;
    }

    @GetMapping("/users/{userId}/sensitive-file-risk")
    public SensitiveFileRiskResponse checkSensitiveFileRisk(
            @PathVariable Long userId) {

        return sensitiveFileDetectionService
                .checkSensitiveFileRisk(userId);
    }
}