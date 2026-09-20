package com.sentineluba.server.detection.controller;

import com.sentineluba.server.detection.dto.UserRiskResponse;
import com.sentineluba.server.detection.service.UserRiskDetectionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/detection")
public class UserRiskController {

    private final UserRiskDetectionService userRiskDetectionService;

    public UserRiskController(
            UserRiskDetectionService userRiskDetectionService) {

        this.userRiskDetectionService =
                userRiskDetectionService;
    }

    @GetMapping("/users/{userId}/risk")
    public UserRiskResponse getUserRisk(
            @PathVariable Long userId) {

        return userRiskDetectionService
                .calculateUserRisk(userId);
    }
}