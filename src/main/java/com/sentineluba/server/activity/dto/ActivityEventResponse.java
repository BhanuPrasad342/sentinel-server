package com.sentineluba.server.activity.dto;

import java.time.LocalDateTime;

public class ActivityEventResponse {

    private Long id;
    private Long userId;
    private String eventType;
    private String action;
    private String resource;
    private String ipAddress;
    private LocalDateTime timestamp;

    public ActivityEventResponse() {
    }

    public ActivityEventResponse(Long id, Long userId, String eventType,
                                 String action, String resource,
                                 String ipAddress, LocalDateTime timestamp) {
        this.id = id;
        this.userId = userId;
        this.eventType = eventType;
        this.action = action;
        this.resource = resource;
        this.ipAddress = ipAddress;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}