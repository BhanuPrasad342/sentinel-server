package com.sentineluba.server.activity.controller;

import com.sentineluba.server.activity.dto.ActivityEventRequest;
import com.sentineluba.server.activity.dto.ActivityEventResponse;
import com.sentineluba.server.activity.service.ActivityEventService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activity-events")
public class ActivityEventController {

    private final ActivityEventService activityEventService;

    public ActivityEventController(ActivityEventService activityEventService) {
        this.activityEventService = activityEventService;
    }

    @PostMapping
    public ActivityEventResponse createEvent(
            @Valid @RequestBody ActivityEventRequest request) {

        return activityEventService.createEvent(request);
    }

    @GetMapping
    public List<ActivityEventResponse> getAllEvents() {

        return activityEventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public ActivityEventResponse getEventById(@PathVariable Long id) {

        return activityEventService.getEventById(id);
    }
    @GetMapping("/user/{userId}")
    public List<ActivityEventResponse> getEventsByUserId(@PathVariable Long userId) {

        return activityEventService.getEventsByUserId(userId);
    }
}