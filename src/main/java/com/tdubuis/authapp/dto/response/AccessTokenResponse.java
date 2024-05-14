package com.tdubuis.authapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class AccessTokenResponse {
    private String accessToken;
    private Date accessTokenExpiresAt;
}
