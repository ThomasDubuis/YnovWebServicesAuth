package com.tdubuis.authapp.controller;

import com.tdubuis.authapp.dto.request.AuthenticationRequest;
import com.tdubuis.authapp.dto.response.AccessTokenResponse;
import com.tdubuis.authapp.dto.response.TokenResponse;
import com.tdubuis.authapp.exception.TooManyRequestsException;
import com.tdubuis.authapp.service.TokenService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@Slf4j
@AllArgsConstructor
public class TokenController {

    private final TokenService tokenService;
    private final Bucket shortTimeBucket = Bucket.builder().addLimit(Bandwidth.builder().capacity(3).refillIntervally(3, Duration.ofMinutes(5)).build()).build();
    private final Bucket longTimeBucket = Bucket.builder().addLimit(Bandwidth.builder().capacity(1).refillIntervally(1, Duration.ofMinutes(30)).build()).build();


    @PostMapping("/refresh-token/{refreshToken}/token")
    public ResponseEntity<TokenResponse> refreshToken(@PathVariable String refreshToken) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tokenService.generateTokenWithRefreshToken(refreshToken));
    }

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> generateToken(@RequestBody AuthenticationRequest authenticationRequest) {
        if(longTimeBucket.tryConsume(1)) {
            longTimeBucket.reset();
            return ResponseEntity.status(HttpStatus.CREATED).body(tokenService.generateToken(authenticationRequest, shortTimeBucket, longTimeBucket));
        }else {
            throw new TooManyRequestsException("Too many failed attempts, please try later");
        }
    }

    @GetMapping("validate/{accessToken}")
    public ResponseEntity<AccessTokenResponse> validateAccessToken(@PathVariable String accessToken) {
        return ResponseEntity.ok(tokenService.validateAccessToken(accessToken));
    }
}
