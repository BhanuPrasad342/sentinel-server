package com.sentineluba.server.detection.service;

import com.sentineluba.server.activity.entity.ActivityEvent;
import com.sentineluba.server.activity.repository.ActivityEventRepository;
import com.sentineluba.server.detection.dto.SensitiveFileRiskResponse;
import com.sentineluba.server.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SensitiveFileDetectionService {

    private static final int RISK_SCORE = 25;
    private static final int WINDOW_MINUTES = 10;

    private final ActivityEventRepository activityEventRepository;
    private final UserRepository userRepository;

    public SensitiveFileDetectionService(
            ActivityEventRepository activityEventRepository,
            UserRepository userRepository) {

        this.activityEventRepository = activityEventRepository;
        this.userRepository = userRepository;
    }

    public SensitiveFileRiskResponse checkSensitiveFileRisk(Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        LocalDateTime windowStart =
                LocalDateTime.now().minusMinutes(WINDOW_MINUTES);

        List<ActivityEvent> events =
                activityEventRepository
                        .findByUserIdAndActionAndTimestampAfter(
                                userId,
                                "DOWNLOAD",
                                windowStart
                        );

        ActivityEvent sensitiveEvent = null;

        for (ActivityEvent event : events) {

            String resource = event.getResource().toLowerCase();

            if (isSensitiveResource(resource)) {
                sensitiveEvent = event;
                break;
            }
        }

        if (sensitiveEvent != null) {

            return new SensitiveFileRiskResponse(
                    userId,
                    sensitiveEvent.getResource(),
                    true,
                    RISK_SCORE,
                    "Sensitive file access detected",
                    LocalDateTime.now()
            );
        }

        return new SensitiveFileRiskResponse(
                userId,
                null,
                false,
                0,
                "No sensitive file access detected",
                LocalDateTime.now()
        );
    }

    private boolean isSensitiveResource(String resource) {

        return resource.contains("payroll")
                || resource.contains("salary")
                || resource.contains("password")
                || resource.contains("credential")
                || resource.contains("customer")
                || resource.contains("employee")
                || resource.contains("confidential")
                || resource.contains("secret");
    }
}