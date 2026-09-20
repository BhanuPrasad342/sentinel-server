package com.sentineluba.server.user.repository;

import com.sentineluba.server.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmployeeId(String employeeId);
}