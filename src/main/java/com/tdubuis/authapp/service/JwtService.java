package com.tdubuis.authapp.service;

import com.tdubuis.authapp.config.TokenProperties;
import com.tdubuis.authapp.dto.response.TokenResponse;
import com.tdubuis.authapp.utils.token.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
@Slf4j
@AllArgsConstructor
public class JwtService {

    private final TokenProperties tokenProperties;

    public void generateAccessToken(TokenResponse tokenResponse, String accountId, long currentTimeMillis) {

        Date expirationDate = new Date(currentTimeMillis + tokenProperties.getAccessTokenExpiration() * 1000);
        String token = Jwts.builder()
                .subject(accountId)
                .issuedAt(new Date(currentTimeMillis))
                .expiration(expirationDate)
                .signWith(getSignInAccessKey(), Jwts.SIG.HS256)
                .compact();
        tokenResponse.setAccessToken(token);
        tokenResponse.setAccessTokenExpiresAt(expirationDate);
    }

    public void generateRefreshToken(TokenResponse tokenResponse, String accountId, long currentTimeMillis) {
        Date expirationDate = new Date(currentTimeMillis + tokenProperties.getRefreshTokenExpiration() * 1000);
        String token = Jwts.builder()
                .subject(accountId)
                .issuedAt(new Date(currentTimeMillis))
                .expiration(expirationDate)
                .signWith(getSignInRefreshKey(), Jwts.SIG.HS256)
                .compact();
        tokenResponse.setRefreshToken(token);
        tokenResponse.setRefreshTokenExpiresAt(expirationDate);
    }

    private SecretKey getSignInAccessKey() {
        byte[] keyBytes = Decoders.BASE64.decode(tokenProperties.getAccessSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private SecretKey getSignInRefreshKey() {
        byte[] keyBytes = Decoders.BASE64.decode(tokenProperties.getRefreshSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isRefreshTokenExpired(String accessToken) {
        return extractExpiration(accessToken, TokenType.REFRESH_TOKEN).before(new Date());
    }
    public boolean isAccessTokenExpired(String accessToken) {
        return extractExpiration(accessToken, TokenType.ACCESS_TOKEN).before(new Date());
    }
    public Date extractExpiration(String token, TokenType tokenType) {
        return extractClaim(token, Claims::getExpiration, tokenType);
    }

    public String extractAccountId(String token, TokenType tokenType) {
        return extractClaim(token, Claims::getSubject, tokenType);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolvers, TokenType tokenType) {
        final Claims claims = extractAllClaims(token, tokenType);
        return claimsResolvers.apply(claims);
    }

    private Claims extractAllClaims(String token, TokenType tokenType) {
        SecretKey secretKey = switch (tokenType) {
            case ACCESS_TOKEN -> getSignInAccessKey();
            case REFRESH_TOKEN -> getSignInRefreshKey();
        };

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
