package com.example.mdbspringbootreactive.controller;

import com.example.mdbspringbootreactive.config.JwtTokenProvider;
import com.example.mdbspringbootreactive.model.Account;
import com.example.mdbspringbootreactive.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtTokenProvider tokenProvider;

    public AuthController(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @GetMapping("/login")
    public Mono<String> login() {
        return Mono.just("Hello");
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(@RequestBody Account request) {
        // Dummy authentication (replace with real logic)
        if ("user".equals(request.getEmail()) && "password".equals(request.getPassword())) {
            String token = tokenProvider.generateToken("user", List.of("USER"));
            return Mono.just(ResponseEntity.ok(token));
        }
        return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
