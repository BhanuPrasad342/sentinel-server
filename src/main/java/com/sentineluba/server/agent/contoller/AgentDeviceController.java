package com.sentineluba.server.agent.controller;

import com.sentineluba.server.agent.dto.DeviceResponse;
import com.sentineluba.server.agent.entity.AgentDevice;
import com.sentineluba.server.agent.repository.AgentDeviceRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class AgentDeviceController {

    private final AgentDeviceRepository repository;

    public AgentDeviceController(AgentDeviceRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<DeviceResponse> getAllDevices() {

        return repository.findAllByOrderByIdDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private DeviceResponse toResponse(AgentDevice device) {

        return new DeviceResponse(
                device.getId(),
                device.getDeviceId(),
                device.getDeviceName(),
                device.getHostname(),
                device.getOperatingSystem(),
                device.getStatus(),
                device.getUser().getEmployeeId(),
                device.getUser().getName()
        );
    }
}