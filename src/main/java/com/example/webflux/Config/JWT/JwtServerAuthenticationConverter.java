package com.example.webflux.Config.JWT;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.security.sasl.AuthenticationException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtServerAuthenticationConverter implements ServerAuthenticationConverter  {
    private final JwtService jwtService;
    private static final String Bearer = "Bearer ";

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .filter(header -> header.startsWith(Bearer))
                .map(header -> header.substring(Bearer.length()))
                .map(token -> {
                    try {
                        return new JwtToken(token, createUserDetails(token));
                    } catch (AuthenticationException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private UserDetails createUserDetails(String token) throws AuthenticationException {
        String username = jwtService.extractNickname(token);
        return User.builder()
                .username(username)
                .authorities(createAuthorities(token))
                .password("")
                .build();
    }

    private List<SimpleGrantedAuthority> createAuthorities(String token) throws AuthenticationException {
        return jwtService.extractRoles(token).stream()
                .map(role -> "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}
