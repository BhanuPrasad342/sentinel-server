package com.sentineluba.server.config;

import com.sentineluba.server.user.entity.ManagerCredential;
import com.sentineluba.server.user.repository.ManagerCredentialRepository;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Component
public class ManagerWebSocketAuthInterceptor
        implements ChannelInterceptor {

    private final ManagerCredentialRepository repository;
    private final PasswordEncoder passwordEncoder;

    public ManagerWebSocketAuthInterceptor(
            ManagerCredentialRepository repository,
            PasswordEncoder passwordEncoder) {

        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Message<?> preSend(
            Message<?> message,
            MessageChannel channel) {

        StompHeaderAccessor accessor =
                StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

            String authorization =
                    accessor.getFirstNativeHeader(
                            "Authorization"
                    );

            if (authorization == null
                    || !authorization.startsWith("Basic ")) {

                throw new MessagingException(
                        "Manager authentication required"
                );
            }

            try {

                String encoded =
                        authorization.substring(6);

                String decoded =
                        new String(
                                Base64.getDecoder().decode(encoded),
                                StandardCharsets.UTF_8
                        );

                int separator =
                        decoded.indexOf(':');

                if (separator <= 0) {

                    throw new MessagingException(
                            "Invalid manager credentials"
                    );
                }

                String username =
                        decoded.substring(0, separator);

                String password =
                        decoded.substring(separator + 1);


                ManagerCredential credential =
                        repository
                                .findByUsernameAndActiveTrue(
                                        username
                                )
                                .orElseThrow(() ->
                                        new MessagingException(
                                                "Invalid manager credentials"
                                        ));


                if (!passwordEncoder.matches(
                        password,
                        credential.getPasswordHash())) {

                    throw new MessagingException(
                            "Invalid manager credentials"
                    );
                }


                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(
                                credential.getUsername(),
                                null,
                                List.of(
                                        new SimpleGrantedAuthority(
                                                "ROLE_MANAGER"
                                        )
                                )
                        );


                accessor.setUser(authentication);


            } catch (IllegalArgumentException e) {

                throw new MessagingException(
                        "Invalid manager credentials"
                );
            }
        }

        return message;
    }
}