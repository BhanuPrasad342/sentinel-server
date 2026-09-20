package com.sentineluba.server.alert.service;

import com.sentineluba.server.alert.dto.AlertResponse;
import com.sentineluba.server.alert.entity.Alert;
import com.sentineluba.server.alert.repository.AlertRepository;
import com.sentineluba.server.detection.dto.UserRiskResponse;
import com.sentineluba.server.user.entity.User;
import com.sentineluba.server.user.repository.UserRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertService {

    private final AlertRepository alertRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public AlertService(
            AlertRepository alertRepository,
            UserRepository userRepository,
            SimpMessagingTemplate messagingTemplate) {

        this.alertRepository = alertRepository;
        this.userRepository = userRepository;
        this.messagingTemplate = messagingTemplate;
    }
    public AlertResponse updateStatus(
            Long alertId,
            String status) {

        Alert alert = alertRepository.findById(alertId)
                .orElseThrow(() ->
                        new RuntimeException("Alert not found"));

        if (!status.equals("OPEN")
                && !status.equals("ACKNOWLEDGED")
                && !status.equals("RESOLVED")) {

            throw new RuntimeException(
                    "Invalid alert status");
        }

        alert.setStatus(status);

        Alert updatedAlert =
                alertRepository.save(alert);

        return toResponse(updatedAlert);
    }
    public AlertResponse createAlert(UserRiskResponse risk) {

        if (risk.getOverallRisk() < 60) {
            return null;
        }

        Optional<Alert> existingAlert =
                alertRepository
                        .findFirstByUserIdAndStatusOrderByCreatedAtDesc(
                                risk.getUserId(),
                                "OPEN"
                        );

        if (existingAlert.isPresent()) {
            return null;
        }

        User user = userRepository.findById(risk.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Alert alert = new Alert();

        alert.setUser(user);
        alert.setRiskScore(risk.getOverallRisk());
        alert.setRiskLevel(risk.getRiskLevel());
        alert.setMessage(risk.getMessage());
        alert.setStatus("OPEN");
        alert.setCreatedAt(LocalDateTime.now());

        Alert savedAlert = alertRepository.save(alert);

        AlertResponse response = toResponse(savedAlert);

        // Send alert to connected manager dashboards
        messagingTemplate.convertAndSend(
                "/topic/alerts",
                response
        );

        System.out.println("=================================");
        System.out.println(" WEBSOCKET ALERT SENT");
        System.out.println("=================================");
        System.out.println("Alert ID: " + response.getId());
        System.out.println("Destination: /topic/alerts");
        System.out.println("=================================");

        return response;
    }

    public List<AlertResponse> getAllAlerts() {

        return alertRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<AlertResponse> getAlertsByUserId(Long userId) {

        return alertRepository.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<AlertResponse> getOpenAlerts() {

        return alertRepository.findByStatus("OPEN")
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private AlertResponse toResponse(Alert alert) {

        return new AlertResponse(
                alert.getId(),
                alert.getUser().getId(),
                alert.getRiskScore(),
                alert.getRiskLevel(),
                alert.getMessage(),
                alert.getStatus(),
                alert.getCreatedAt()
        );
    }
}