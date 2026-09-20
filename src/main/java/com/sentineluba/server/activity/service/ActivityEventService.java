package com.sentineluba.server.activity.service;

import com.sentineluba.server.activity.dto.ActivityEventRequest;
import com.sentineluba.server.activity.dto.ActivityEventResponse;
import com.sentineluba.server.activity.entity.ActivityEvent;
import com.sentineluba.server.activity.repository.ActivityEventRepository;
import com.sentineluba.server.alert.dto.AlertResponse;
import com.sentineluba.server.alert.service.AlertService;
import com.sentineluba.server.detection.dto.UserRiskResponse;
import com.sentineluba.server.detection.service.UserRiskDetectionService;
import com.sentineluba.server.user.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActivityEventService {

    private final ActivityEventRepository activityEventRepository;
    private final UserRiskDetectionService userRiskDetectionService;
    private final AlertService alertService;

    public ActivityEventService(
            ActivityEventRepository activityEventRepository,
            UserRiskDetectionService userRiskDetectionService,
            AlertService alertService) {

        this.activityEventRepository = activityEventRepository;
        this.userRiskDetectionService = userRiskDetectionService;
        this.alertService = alertService;
    }

    public ActivityEventResponse createEvent(
            ActivityEventRequest request) {

        /*
         * IMPORTANT SECURITY CHANGE:
         *
         * Do NOT trust userId from the request body.
         *
         * The AgentAuthenticationFilter already authenticated
         * the agent and stored the real User as the principal.
         */
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null
                || !(authentication.getPrincipal() instanceof User)) {

            throw new RuntimeException(
                    "Authenticated agent user not found");
        }

        User user =
                (User) authentication.getPrincipal();

        ActivityEvent event = new ActivityEvent();

        /*
         * The user is taken from the authenticated agent credential.
         * request.getUserId() is intentionally NOT used.
         */
        event.setUser(user);

        event.setEventType(request.getEventType());
        event.setAction(request.getAction());
        event.setResource(request.getResource());
        event.setIpAddress(request.getIpAddress());
        event.setTimestamp(LocalDateTime.now());

        ActivityEvent savedEvent =
                activityEventRepository.save(event);

        // Calculate risk automatically
        UserRiskResponse risk =
                userRiskDetectionService
                        .calculateUserRisk(user.getId());

        // Create alert if risk is HIGH or CRITICAL
        AlertResponse alert =
                alertService.createAlert(risk);

        if (alert != null) {

            System.out.println("=================================");
            System.out.println(" ALERT CREATED");
            System.out.println("=================================");
            System.out.println("Alert ID: " + alert.getId());
            System.out.println("User ID: " + alert.getUserId());
            System.out.println("Risk Score: " + alert.getRiskScore());
            System.out.println("Risk Level: " + alert.getRiskLevel());
            System.out.println("Message: " + alert.getMessage());
            System.out.println("Status: " + alert.getStatus());
            System.out.println("=================================");
        }

        System.out.println("=================================");
        System.out.println(" REAL-TIME RISK DETECTION");
        System.out.println("=================================");
        System.out.println("User ID: " + risk.getUserId());
        System.out.println("Overall Risk: " + risk.getOverallRisk());
        System.out.println("Risk Level: " + risk.getRiskLevel());
        System.out.println("Message: " + risk.getMessage());
        System.out.println("=================================");

        return toResponse(savedEvent);
    }

    public List<ActivityEventResponse> getAllEvents() {

        return activityEventRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ActivityEventResponse getEventById(Long id) {

        ActivityEvent event =
                activityEventRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Activity event not found"));

        return toResponse(event);
    }

    public List<ActivityEventResponse> getEventsByUserId(
            Long userId) {

        return activityEventRepository.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private ActivityEventResponse toResponse(ActivityEvent event) {

        return new ActivityEventResponse(
                event.getId(),
                event.getUser().getId(),
                event.getEventType(),
                event.getAction(),
                event.getResource(),
                event.getIpAddress(),
                event.getTimestamp()
        );
    }
}