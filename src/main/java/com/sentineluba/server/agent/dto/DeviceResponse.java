package com.sentineluba.server.agent.dto;

public class DeviceResponse {

    private Long id;
    private String deviceId;
    private String deviceName;
    private String hostname;
    private String operatingSystem;
    private String status;
    private String employeeId;
    private String employeeName;

    public DeviceResponse() {
    }

    public DeviceResponse(
            Long id,
            String deviceId,
            String deviceName,
            String hostname,
            String operatingSystem,
            String status,
            String employeeId,
            String employeeName
    ) {
        this.id = id;
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.hostname = hostname;
        this.operatingSystem = operatingSystem;
        this.status = status;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
    }

    public Long getId() {
        return id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getHostname() {
        return hostname;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public String getStatus() {
        return status;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }
}