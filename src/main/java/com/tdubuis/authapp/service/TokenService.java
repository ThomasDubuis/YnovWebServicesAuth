package com.tdubuis.authapp.service;

import com.tdubuis.authapp.dto.request.AuthenticationRequest;
import com.tdubuis.authapp.dto.response.AccessTokenResponse;
import com.tdubuis.authapp.dto.response.TokenResponse;
import com.tdubuis.authapp.entity.Account;
import com.tdubuis.authapp.exception.ElementNotFoundException;
import com.tdubuis.authapp.repository.AccountRepository;
import com.tdubuis.authapp.utils.token.TokenType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class TokenService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final JwtService jwtService;

    public TokenResponse generateToken(AuthenticationRequest authenticationRequest) {
        Optional<Account> accountOptional = accountRepository.findAccountByLogin(authenticationRequest.getLogin());

        Account account = accountOptional.orElseThrow(() -> new ElementNotFoundException("Identifiants non trouvé (paire login / mot de passe inconnue)"));

        if(passwordEncoder.matches(authenticationRequest.getPassword(), account.getPassword())) {
            TokenResponse tokenResponse = new TokenResponse();
            long currentTimeMillis = System.currentTimeMillis();
            jwtService.generateAccessToken(tokenResponse, account.getUid(), currentTimeMillis);
            jwtService.generateRefreshToken(tokenResponse, account.getUid(), currentTimeMillis);
            return tokenResponse;
        }else {
            throw new ElementNotFoundException("Identifiants non trouvé (paire login / mot de passe inconnue)");
        }
    }

    public AccessTokenResponse validateAccessToken(String accessToken) {
        if (jwtService.isAccessTokenExpired(accessToken)) {
            throw new ElementNotFoundException("Token non trouvé / invalide");
        } else {
            return new AccessTokenResponse(accessToken, jwtService.extractExpiration(accessToken, TokenType.ACCESS_TOKEN));
        }
    }

    public TokenResponse generateTokenWithRefreshToken(String refreshToken) {
        if (jwtService.isRefreshTokenExpired(refreshToken)) {
            throw new ElementNotFoundException("Token non trouvé / invalide");
        } else {
            String accountId = jwtService.extractAccountId(refreshToken, TokenType.REFRESH_TOKEN);
            TokenResponse tokenResponse = new TokenResponse();
            long currentTimeMillis = System.currentTimeMillis();
            jwtService.generateAccessToken(tokenResponse, accountId, currentTimeMillis);
            jwtService.generateRefreshToken(tokenResponse, accountId, currentTimeMillis);
            return tokenResponse;
        }
    }
}
