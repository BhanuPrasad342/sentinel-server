package com.sentineluba.server.detection.controller;

import com.sentineluba.server.detection.dto.FileSharingRiskResponse;
import com.sentineluba.server.detection.service.FileSharingDetectionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/detection")
public class FileSharingDetectionController {

    private final FileSharingDetectionService fileSharingDetectionService;

    public FileSharingDetectionController(
            FileSharingDetectionService fileSharingDetectionService) {

        this.fileSharingDetectionService =
                fileSharingDetectionService;
    }

    @GetMapping("/users/{userId}/file-sharing-risk")
    public FileSharingRiskResponse checkFileSharingRisk(
            @PathVariable Long userId) {

        return fileSharingDetectionService
                .checkFileSharingRisk(userId);
    }
}