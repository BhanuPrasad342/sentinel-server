package com.sentineluba.server.alert.repository;

import com.sentineluba.server.alert.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlertRepository
        extends JpaRepository<Alert, Long> {

    List<Alert> findByUserId(Long userId);

    List<Alert> findByStatus(String status);

    Optional<Alert> findFirstByUserIdAndStatusOrderByCreatedAtDesc(
            Long userId,
            String status
    );
}