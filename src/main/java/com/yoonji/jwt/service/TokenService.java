package com.yoonji.jwt.service;


import com.yoonji.jwt.dto.response.TokenResponse;
import com.yoonji.jwt.entity.RefreshToken;
import com.yoonji.jwt.entity.User;
import com.yoonji.jwt.repository.RefreshTokenRepository;
import com.yoonji.jwt.repository.UserRepository;
import com.yoonji.jwt.security.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;

    @Transactional(readOnly = true)
    public boolean isRevokedRefreshToken(String username) {
        return refreshTokenRepository.findByUserEmail(username)
                .map(RefreshToken::isRevoked)
                .orElseThrow(() -> new RuntimeException("Refresh token not found for user"));
    }

    // refresh token 을 사용해  access token, refresh token 재발급
    @Transactional
    public TokenResponse refreshToken(String refreshToken) {
        if(!jwtProvider.validateToken(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        String username = jwtProvider.getUsername(refreshToken);
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email " + username));

        RefreshToken savedRefreshToken = refreshTokenRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Refresh token not found for user"));

        if (!savedRefreshToken.getToken().equals(refreshToken) || !savedRefreshToken.isValid() ) {
            throw new RuntimeException("Refresh token is expired or invalid");
        }

        String newAccessToken = jwtProvider.createAccessToken(username);
        String newRefreshToken = jwtProvider.createRefreshToken(username);

        savedRefreshToken.update(newRefreshToken, getExpireDate(), false);

        return new TokenResponse(newAccessToken, newRefreshToken);
    }


    private LocalDateTime getExpireDate() {
        return LocalDateTime.now().plusSeconds(jwtProvider.getRefreshTokenExpirationPeriod() / 1000L);
    }


}
