package com.sentineluba.server.user.repository;

import com.sentineluba.server.user.entity.ManagerCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerCredentialRepository
        extends JpaRepository<ManagerCredential, Long> {

    Optional<ManagerCredential> findByUsernameAndActiveTrue(
            String username
    );
}