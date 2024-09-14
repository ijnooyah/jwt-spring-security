package com.yoonji.jwt.controller;

import com.yoonji.jwt.dto.request.LoginRequest;
import com.yoonji.jwt.dto.request.SignupRequest;
import com.yoonji.jwt.dto.response.TokenResponse;
import com.yoonji.jwt.dto.response.UserResponse;
import com.yoonji.jwt.security.user.UserAdapter;
import com.yoonji.jwt.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal UserAdapter adapter) {
        authService.logout(adapter);
        return ResponseEntity.ok().body("Successfully logged out");
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@RequestBody SignupRequest request) {
        UserResponse userResponse = authService.signup(request);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        TokenResponse tokenResponse = authService.login(request);
        return ResponseEntity.ok(tokenResponse);
    }

}
