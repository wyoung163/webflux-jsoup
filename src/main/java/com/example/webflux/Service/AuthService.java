package com.example.webflux.Service;

import com.example.webflux.Domain.User;
import com.example.webflux.Repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    @Value("${kakao.redirect_uri}")
    String redirectUri;

    @Value("${kakao.token_uri}")
    String tokenUri;

    @Value("${kakao.userinfo_uri}")
    String userInfoUri;

    @Value("${kakao.client_id}")
    String clientId;

    private final UserRepository userRepository;

    public String getAccessTokenFromKakao(String code) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("code", code);
        body.add("redirect_uri", redirectUri);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                tokenUri,
                HttpMethod.POST,
                request,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        //log.debug("[User Token] " + jsonNode.asText());
        return jsonNode.get("access_token").asText();
    }

    public Mono<Void> postUserInfo(String access_token) throws JsonProcessingException{
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer" + access_token);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                userInfoUri,
                HttpMethod.POST,
                request,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        Long kakao_id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("nickname").asText();
        String email = jsonNode.get("email").asText();

        return userRepository.save(
                User.builder()
                        .kakao_id(kakao_id)
                        .nickname(nickname)
                        .email(email)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        ).then();
    }
}
