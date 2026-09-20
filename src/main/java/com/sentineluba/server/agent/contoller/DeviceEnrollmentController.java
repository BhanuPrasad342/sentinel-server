package com.sentineluba.server.agent.controller;

import com.sentineluba.server.agent.dto.DeviceEnrollmentRequest;
import com.sentineluba.server.agent.dto.DeviceEnrollmentResponse;
import com.sentineluba.server.agent.service.DeviceEnrollmentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agent/devices")
public class DeviceEnrollmentController {

    private final DeviceEnrollmentService deviceEnrollmentService;

    public DeviceEnrollmentController(
            DeviceEnrollmentService deviceEnrollmentService) {

        this.deviceEnrollmentService = deviceEnrollmentService;
    }

    @PostMapping("/enroll")
    public DeviceEnrollmentResponse enrollDevice(
            @Valid @RequestBody DeviceEnrollmentRequest request) {

        return deviceEnrollmentService.enrollDevice(request);
    }
}