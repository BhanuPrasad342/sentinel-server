package com.sentineluba.server.agent.dto;

public class DeviceEnrollmentResponse {

    private Long deviceId;
    private String deviceIdentifier;
    private String deviceName;
    private String agentKey;

    public DeviceEnrollmentResponse(
            Long deviceId,
            String deviceIdentifier,
            String deviceName,
            String agentKey) {

        this.deviceId = deviceId;
        this.deviceIdentifier = deviceIdentifier;
        this.deviceName = deviceName;
        this.agentKey = agentKey;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public String getDeviceIdentifier() {
        return deviceIdentifier;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getAgentKey() {
        return agentKey;
    }
}