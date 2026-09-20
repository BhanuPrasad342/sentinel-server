package com.sentineluba.server.detection.controller;

import com.sentineluba.server.detection.dto.UnusualLoginTimeRiskResponse;
import com.sentineluba.server.detection.service.UnusualLoginTimeDetectionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/detection")
public class UnusualLoginTimeDetectionController {

    private final UnusualLoginTimeDetectionService
            unusualLoginTimeDetectionService;

    public UnusualLoginTimeDetectionController(
            UnusualLoginTimeDetectionService
                    unusualLoginTimeDetectionService) {

        this.unusualLoginTimeDetectionService =
                unusualLoginTimeDetectionService;
    }

    @GetMapping("/users/{userId}/unusual-login-time-risk")
    public UnusualLoginTimeRiskResponse checkLoginTimeRisk(
            @PathVariable Long userId) {

        return unusualLoginTimeDetectionService
                .checkLoginTimeRisk(userId);
    }
}