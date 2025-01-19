package com.example.webflux.Controller;

import com.example.webflux.Config.JWT.JwtService;
import com.example.webflux.Service.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    private final JwtService jwtService;

    @GetMapping("/callback")
    //public ResponseEntity<Mono<User>> callback(@RequestParam("code") String code) throws JsonProcessingException
    public Mono<String> callback(@RequestParam("code") String code) throws JsonProcessingException {
        String access_token = authService.getAccessTokenFromKakao(code);
        return authService.postUserInfo(access_token)
                .map(jwtService::generateToken);

        /*
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, "Authorization=Bearer " + user
                        .map(jwtService::generateToken)
                        .map(String::new)
                        .switchIfEmpty(Mono.error(new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED
                ))))
                .body(
                    user
                );

         */
    }
}
