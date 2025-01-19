package com.example.webflux.Config;

import com.example.webflux.Config.JWT.JwtService;
import com.example.webflux.Config.JWT.JwtToken;
import com.example.webflux.Config.JWT.JwtTokenAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.server.WebFilterChain;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
                                                         JwtService jwtService,
                                                         ReactiveAuthenticationManager authenticationManager,
                                                         ServerAuthenticationConverter authenticationConverter) {
        /*
            ServerAuthenticationConverter: 서버 exchange를 인증 객체로 변환
            ReactiveAuthenticationManager:
         */
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(authenticationManager);
        authenticationWebFilter.setServerAuthenticationConverter(authenticationConverter);


        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .cors(ServerHttpSecurity.CorsSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/callback").permitAll()
                        .pathMatchers("/**").permitAll()
                        .anyExchange().authenticated() // /auth를 제외한 모든 path는 인증 요구됨
                )
                //.addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .addFilterAt(new JwtTokenAuthenticationFilter(jwtService), SecurityWebFiltersOrder.HTTP_BASIC)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance()); // stateless session

        return http.build();
    }
}
