package com.sentineluba.server.activity.repository;

import com.sentineluba.server.activity.entity.ActivityEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ActivityEventRepository
        extends JpaRepository<ActivityEvent, Long> {

    List<ActivityEvent> findByUserId(Long userId);

    long countByUserIdAndActionAndTimestampAfter(
            Long userId,
            String action,
            LocalDateTime timestamp
    );

    List<ActivityEvent> findByUserIdAndActionAndTimestampAfter(
            Long userId,
            String action,
            LocalDateTime timestamp
    );
}