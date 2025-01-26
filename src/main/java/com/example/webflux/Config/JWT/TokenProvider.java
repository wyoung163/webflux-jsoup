package com.example.webflux.Config.JWT;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface TokenProvider {
    String generateToken(UserDetails userDetails);
}
