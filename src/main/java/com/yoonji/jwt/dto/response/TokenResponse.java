package com.yoonji.jwt.dto.response;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
public class TokenResponse {
    private String accessToken;
    private String refreshToken;

    @Builder
    public TokenResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
