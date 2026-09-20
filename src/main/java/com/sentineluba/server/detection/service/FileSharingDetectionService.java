package com.sentineluba.server.detection.service;

import com.sentineluba.server.activity.entity.ActivityEvent;
import com.sentineluba.server.activity.repository.ActivityEventRepository;
import com.sentineluba.server.detection.dto.FileSharingRiskResponse;
import com.sentineluba.server.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FileSharingDetectionService {

    private static final int RISK_SCORE = 30;

    private final ActivityEventRepository activityEventRepository;
    private final UserRepository userRepository;

    public FileSharingDetectionService(
            ActivityEventRepository activityEventRepository,
            UserRepository userRepository) {

        this.activityEventRepository = activityEventRepository;
        this.userRepository = userRepository;
    }

    public FileSharingRiskResponse checkFileSharingRisk(Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<ActivityEvent> events =
                activityEventRepository.findByUserId(userId);

        for (ActivityEvent event : events) {

            String action = event.getAction();

            if (isFileSharingAction(action)) {

                return new FileSharingRiskResponse(
                        userId,
                        event.getResource(),
                        action,
                        true,
                        RISK_SCORE,
                        "File sharing or external transfer detected",
                        LocalDateTime.now()
                );
            }
        }

        return new FileSharingRiskResponse(
                userId,
                null,
                null,
                false,
                0,
                "No file sharing activity detected",
                LocalDateTime.now()
        );
    }

    private boolean isFileSharingAction(String action) {

        if (action == null) {
            return false;
        }

        return action.equalsIgnoreCase("SHARE")
                || action.equalsIgnoreCase("UPLOAD")
                || action.equalsIgnoreCase("EXTERNAL_TRANSFER")
                || action.equalsIgnoreCase("CLOUD_UPLOAD")
                || action.equalsIgnoreCase("EMAIL_ATTACHMENT");
    }
}