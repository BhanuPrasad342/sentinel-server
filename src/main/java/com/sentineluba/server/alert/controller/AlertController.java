package com.sentineluba.server.alert.controller;

import com.sentineluba.server.alert.dto.AlertResponse;
import com.sentineluba.server.alert.service.AlertService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping
    public List<AlertResponse> getAllAlerts() {
        return alertService.getAllAlerts();
    }

    @GetMapping("/open")
    public List<AlertResponse> getOpenAlerts() {
        return alertService.getOpenAlerts();
    }

    @GetMapping("/user/{userId}")
    public List<AlertResponse> getAlertsByUserId(
            @PathVariable Long userId) {

        return alertService.getAlertsByUserId(userId);
    }

    @PutMapping("/{alertId}/status")
    public AlertResponse updateStatus(
            @PathVariable Long alertId,
            @RequestParam String status) {

        return alertService.updateStatus(
                alertId,
                status
        );
    }
}