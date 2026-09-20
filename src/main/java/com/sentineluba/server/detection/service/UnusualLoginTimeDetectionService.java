package com.sentineluba.server.detection.service;

import com.sentineluba.server.activity.entity.ActivityEvent;
import com.sentineluba.server.activity.repository.ActivityEventRepository;
import com.sentineluba.server.detection.dto.UnusualLoginTimeRiskResponse;
import com.sentineluba.server.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class UnusualLoginTimeDetectionService {

    private static final int RISK_SCORE = 15;

    private static final LocalTime WORK_START =
            LocalTime.of(9, 0);

    private static final LocalTime WORK_END =
            LocalTime.of(18, 0);

    private final ActivityEventRepository activityEventRepository;
    private final UserRepository userRepository;

    public UnusualLoginTimeDetectionService(
            ActivityEventRepository activityEventRepository,
            UserRepository userRepository) {

        this.activityEventRepository = activityEventRepository;
        this.userRepository = userRepository;
    }

    public UnusualLoginTimeRiskResponse checkLoginTimeRisk(
            Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<ActivityEvent> events =
                activityEventRepository.findByUserId(userId);

        ActivityEvent latestLogin = null;

        for (ActivityEvent event : events) {

            if ("LOGIN".equalsIgnoreCase(event.getAction())) {
                latestLogin = event;
            }
        }

        if (latestLogin == null) {

            return new UnusualLoginTimeRiskResponse(
                    userId,
                    null,
                    false,
                    0,
                    "No login activity found",
                    LocalDateTime.now()
            );
        }

        LocalDateTime loginTime =
                latestLogin.getTimestamp();

        boolean unusual =
                isUnusualLoginTime(loginTime);

        int riskScore =
                unusual ? RISK_SCORE : 0;

        String message =
                unusual
                        ? "Unusual login time detected"
                        : "Login time is normal";

        return new UnusualLoginTimeRiskResponse(
                userId,
                loginTime,
                unusual,
                riskScore,
                message,
                LocalDateTime.now()
        );
    }

    private boolean isUnusualLoginTime(
            LocalDateTime loginTime) {

        DayOfWeek day =
                loginTime.getDayOfWeek();

        LocalTime time =
                loginTime.toLocalTime();

        boolean weekday =
                day != DayOfWeek.SATURDAY
                        && day != DayOfWeek.SUNDAY;

        boolean workingHours =
                !time.isBefore(WORK_START)
                        && time.isBefore(WORK_END);

        return !weekday || !workingHours;
    }
}