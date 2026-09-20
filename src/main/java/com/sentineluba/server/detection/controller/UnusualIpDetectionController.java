package com.sentineluba.server.detection.controller;

import com.sentineluba.server.detection.dto.UnusualIpRiskResponse;
import com.sentineluba.server.detection.service.UnusualIpDetectionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/detection")
public class UnusualIpDetectionController {

    private final UnusualIpDetectionService unusualIpDetectionService;

    public UnusualIpDetectionController(
            UnusualIpDetectionService unusualIpDetectionService) {

        this.unusualIpDetectionService =
                unusualIpDetectionService;
    }

    @GetMapping("/users/{userId}/unusual-ip-risk")
    public UnusualIpRiskResponse checkUnusualIpRisk(
            @PathVariable Long userId) {

        return unusualIpDetectionService
                .checkUnusualIpRisk(userId);
    }
}