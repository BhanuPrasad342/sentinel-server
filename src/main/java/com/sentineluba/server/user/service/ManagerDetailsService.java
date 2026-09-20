package com.sentineluba.server.user.service;

import com.sentineluba.server.user.entity.ManagerCredential;
import com.sentineluba.server.user.repository.ManagerCredentialRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ManagerDetailsService implements UserDetailsService {

    private final ManagerCredentialRepository repository;

    public ManagerDetailsService(
            ManagerCredentialRepository repository) {

        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        ManagerCredential credential =
                repository
                        .findByUsernameAndActiveTrue(username)
                        .orElseThrow(() ->
                                new UsernameNotFoundException(
                                        "Manager account not found"
                                ));

        return org.springframework.security.core.userdetails.User
                .withUsername(credential.getUsername())
                .password(credential.getPasswordHash())
                .roles("MANAGER")
                .disabled(!credential.isActive())
                .build();
    }
}