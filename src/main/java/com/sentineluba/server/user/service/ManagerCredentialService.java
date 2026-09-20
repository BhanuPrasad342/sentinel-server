package com.sentineluba.server.user.service;

import com.sentineluba.server.user.entity.ManagerCredential;
import com.sentineluba.server.user.entity.User;
import com.sentineluba.server.user.repository.ManagerCredentialRepository;
import com.sentineluba.server.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ManagerCredentialService {

    private final UserRepository userRepository;
    private final ManagerCredentialRepository managerCredentialRepository;
    private final PasswordEncoder passwordEncoder;

    public ManagerCredentialService(
            UserRepository userRepository,
            ManagerCredentialRepository managerCredentialRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.managerCredentialRepository = managerCredentialRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void createManagerCredential(
            Long userId,
            String username,
            String password) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (!"MANAGER".equalsIgnoreCase(user.getRole())) {
            throw new RuntimeException(
                    "User is not a manager");
        }

        if (managerCredentialRepository
                .findByUsernameAndActiveTrue(username)
                .isPresent()) {

            throw new RuntimeException(
                    "Manager username already exists");
        }

        ManagerCredential credential =
                new ManagerCredential();

        credential.setUsername(username);
        credential.setPasswordHash(
                passwordEncoder.encode(password)
        );
        credential.setActive(true);
        credential.setUser(user);

        managerCredentialRepository.save(credential);
    }
}