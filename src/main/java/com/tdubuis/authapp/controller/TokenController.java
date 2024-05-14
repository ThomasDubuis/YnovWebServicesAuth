package com.tdubuis.authapp.controller;

import com.tdubuis.authapp.dto.request.AuthenticationRequest;
import com.tdubuis.authapp.dto.response.AccessTokenResponse;
import com.tdubuis.authapp.dto.response.TokenResponse;
import com.tdubuis.authapp.service.TokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/refresh-token/{refreshToken}/token")
    public ResponseEntity<TokenResponse> refreshToken(@PathVariable String refreshToken) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tokenService.generateTokenWithRefreshToken(refreshToken));
    }

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> generateToken(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tokenService.generateToken(authenticationRequest));
    }

    @GetMapping("validate/{accessToken}")
    public ResponseEntity<AccessTokenResponse> validateAccessToken(@PathVariable String accessToken) {
        return ResponseEntity.ok(tokenService.validateAccessToken(accessToken));
    }

}
