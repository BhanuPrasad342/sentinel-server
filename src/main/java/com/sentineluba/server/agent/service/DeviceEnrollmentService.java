package com.sentineluba.server.agent.service;

import com.sentineluba.server.agent.dto.DeviceEnrollmentRequest;
import com.sentineluba.server.agent.dto.DeviceEnrollmentResponse;
import com.sentineluba.server.agent.entity.AgentCredential;
import com.sentineluba.server.agent.entity.AgentDevice;
import com.sentineluba.server.agent.repository.AgentCredentialRepository;
import com.sentineluba.server.agent.repository.AgentDeviceRepository;
import com.sentineluba.server.user.entity.User;
import com.sentineluba.server.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class DeviceEnrollmentService {

    private final UserRepository userRepository;
    private final AgentDeviceRepository agentDeviceRepository;
    private final AgentCredentialRepository agentCredentialRepository;

    private final SecureRandom secureRandom = new SecureRandom();

    public DeviceEnrollmentService(
            UserRepository userRepository,
            AgentDeviceRepository agentDeviceRepository,
            AgentCredentialRepository agentCredentialRepository) {

        this.userRepository = userRepository;
        this.agentDeviceRepository = agentDeviceRepository;
        this.agentCredentialRepository = agentCredentialRepository;
    }

    @Transactional
    public DeviceEnrollmentResponse enrollDevice(
            DeviceEnrollmentRequest request) {

        // 1. Find employee
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("Employee not found"));

        // 2. Check whether device is already enrolled
        if (agentDeviceRepository
                .findByDeviceId(request.getDeviceId())
                .isPresent()) {

            throw new RuntimeException(
                    "Device is already enrolled");
        }

        // 3. Create device
        AgentDevice device = new AgentDevice();

        device.setDeviceId(request.getDeviceId());
        device.setDeviceName(request.getDeviceName());
        device.setHostname(request.getHostname());
        device.setOperatingSystem(request.getOperatingSystem());
        device.setStatus("ACTIVE");
        device.setUser(user);

        AgentDevice savedDevice =
                agentDeviceRepository.save(device);

        // 4. Generate secure credential
        String agentKey = generateAgentKey();

        // 5. Create credential
        AgentCredential credential = new AgentCredential();

        credential.setAgentKey(agentKey);
        credential.setActive(true);
        credential.setDevice(savedDevice);

        agentCredentialRepository.save(credential);

        // 6. Return enrollment information
        return new DeviceEnrollmentResponse(
                savedDevice.getId(),
                savedDevice.getDeviceId(),
                savedDevice.getDeviceName(),
                agentKey
        );
    }

    private String generateAgentKey() {

        byte[] randomBytes = new byte[32];

        secureRandom.nextBytes(randomBytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);
    }
}