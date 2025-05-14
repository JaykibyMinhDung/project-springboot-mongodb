package com.example.mdbspringbootreactive.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final SecretKey key;

    public JwtAuthenticationManager(@Value("${jwt.secret}") String secret) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(secret);
            this.key = new SecretKeySpec(keyBytes, "HmacSHA512");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Base64-encoded jwt.secret: " + e.getMessage(), e);
        }
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();

        return Mono.just(authToken)
                .map(token -> {
                    try {
                        return Jwts.parserBuilder()
                                .setSigningKey(key)
                                .build()
                                .parseClaimsJws(token)
                                .getBody();
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(claims -> claims != null)
                .map(claims -> {
                    String username = claims.getSubject();
                    List<String> roles = claims.get("roles", List.class);

                    List<SimpleGrantedAuthority> authorities = roles.stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                            .collect(Collectors.toList());

                    return new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            authorities
                    );
                });
    }
}
