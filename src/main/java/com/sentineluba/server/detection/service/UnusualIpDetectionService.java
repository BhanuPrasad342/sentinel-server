package com.sentineluba.server.detection.service;

import com.sentineluba.server.activity.entity.ActivityEvent;
import com.sentineluba.server.activity.repository.ActivityEventRepository;
import com.sentineluba.server.detection.dto.UnusualIpRiskResponse;
import com.sentineluba.server.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UnusualIpDetectionService {

    private static final int RISK_SCORE = 20;
    private static final String NORMAL_IP = "127.0.0.1";

    private final ActivityEventRepository activityEventRepository;
    private final UserRepository userRepository;

    public UnusualIpDetectionService(
            ActivityEventRepository activityEventRepository,
            UserRepository userRepository) {

        this.activityEventRepository = activityEventRepository;
        this.userRepository = userRepository;
    }

    public UnusualIpRiskResponse checkUnusualIpRisk(Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<ActivityEvent> events =
                activityEventRepository.findByUserId(userId);

        for (ActivityEvent event : events) {

            String ipAddress = event.getIpAddress();

            if (ipAddress != null
                    && !NORMAL_IP.equals(ipAddress)) {

                return new UnusualIpRiskResponse(
                        userId,
                        ipAddress,
                        true,
                        RISK_SCORE,
                        "Unusual IP address detected",
                        LocalDateTime.now()
                );
            }
        }

        return new UnusualIpRiskResponse(
                userId,
                NORMAL_IP,
                false,
                0,
                "IP address is normal",
                LocalDateTime.now()
        );
    }
}