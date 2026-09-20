package com.sentineluba.server.user.controller;

import com.sentineluba.server.user.service.ManagerCredentialService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/manager")
public class ManagerCredentialController {

    private final ManagerCredentialService managerCredentialService;

    public ManagerCredentialController(
            ManagerCredentialService managerCredentialService) {

        this.managerCredentialService = managerCredentialService;
    }

    @PostMapping("/credentials")
    public String createManagerCredential(
            @RequestParam Long userId,
            @RequestParam String username,
            @RequestParam String password) {

        managerCredentialService.createManagerCredential(
                userId,
                username,
                password
        );

        return "Manager credential created successfully";
    }
}