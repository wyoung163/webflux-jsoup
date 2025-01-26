package com.example.webflux.Config.JWT;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.security.sasl.AuthenticationException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {
    private final JwtService jwtService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication)
                .cast(JwtToken.class)
                .filter(jwtToken -> {
                    try {
                        return jwtService.isTokenValid(jwtToken.getToken());
                    } catch (AuthenticationException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(jwtToken -> jwtToken.withAuthenticated(true))
                .switchIfEmpty(Mono.error(new Exception("Invalid Token")));
    }
}
