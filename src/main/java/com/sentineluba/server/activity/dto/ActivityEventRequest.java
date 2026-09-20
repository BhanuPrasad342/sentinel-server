package com.sentineluba.server.activity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ActivityEventRequest {

    @NotNull
    private Long userId;

    @NotBlank
    private String eventType;

    @NotBlank
    private String action;

    @NotBlank
    private String resource;

    @NotBlank
    private String ipAddress;

    public ActivityEventRequest() {
    }

    public ActivityEventRequest(Long userId, String eventType, String action,
                                String resource, String ipAddress) {
        this.userId = userId;
        this.eventType = eventType;
        this.action = action;
        this.resource = resource;
        this.ipAddress = ipAddress;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}