package com.example.webflux.Controller;

import com.example.webflux.Domain.User;
import com.example.webflux.Service.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class AuthController {
    @Value("${kakao.client_id")
    String clientId;

    @Value("${kakao.redirect_uri")
    String redirectUri;

    private final AuthService authService;

    @GetMapping("/callback")
    public ResponseEntity<Mono<User>> callback(@RequestParam("code") String code) throws JsonProcessingException {
        String access_token = authService.getAccessTokenFromKakao(code);
        authService.postUserInfo(access_token);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, "Authorization=Bearer "+access_token)
                .body(
                    authService.postUserInfo(access_token)
                );
    }
}
