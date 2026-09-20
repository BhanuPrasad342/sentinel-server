package com.sentineluba.server.detection.service;

import com.sentineluba.server.activity.repository.ActivityEventRepository;
import com.sentineluba.server.detection.dto.DownloadRiskResponse;
import com.sentineluba.server.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DownloadDetectionService {

    private static final int DOWNLOAD_THRESHOLD = 5;
    private static final int WINDOW_MINUTES = 10;
    private static final int RISK_SCORE = 30;

    private final ActivityEventRepository activityEventRepository;
    private final UserRepository userRepository;

    public DownloadDetectionService(
            ActivityEventRepository activityEventRepository,
            UserRepository userRepository) {

        this.activityEventRepository = activityEventRepository;
        this.userRepository = userRepository;
    }

    public DownloadRiskResponse checkDownloadRisk(Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        LocalDateTime windowStart =
                LocalDateTime.now().minusMinutes(WINDOW_MINUTES);

        long downloadCount =
                activityEventRepository
                        .countByUserIdAndActionAndTimestampAfter(
                                userId,
                                "DOWNLOAD",
                                windowStart
                        );

        boolean suspicious =
                downloadCount >= DOWNLOAD_THRESHOLD;

        int riskScore =
                suspicious ? RISK_SCORE : 0;

        String message;

        if (suspicious) {
            message = "Excessive downloads detected";
        } else {
            message = "Download activity is normal";
        }

        return new DownloadRiskResponse(
                userId,
                downloadCount,
                DOWNLOAD_THRESHOLD,
                WINDOW_MINUTES,
                suspicious,
                riskScore,
                message,
                LocalDateTime.now()
        );
    }
}