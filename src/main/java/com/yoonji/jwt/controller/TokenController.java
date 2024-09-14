package com.yoonji.jwt.controller;

import com.yoonji.jwt.dto.request.RefreshTokenRequest;
import com.yoonji.jwt.dto.response.TokenResponse;
import com.yoonji.jwt.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/token")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        TokenResponse tokenResponse = tokenService.refreshToken(request.getRefreshToken());
        return ResponseEntity.ok(tokenResponse);
    }
}
