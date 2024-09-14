package com.yoonji.jwt.service;

import com.yoonji.jwt.dto.request.LoginRequest;
import com.yoonji.jwt.dto.request.SignupRequest;
import com.yoonji.jwt.dto.response.TokenResponse;
import com.yoonji.jwt.dto.response.UserResponse;
import com.yoonji.jwt.entity.RefreshToken;
import com.yoonji.jwt.entity.RoleType;
import com.yoonji.jwt.entity.User;
import com.yoonji.jwt.repository.RefreshTokenRepository;
import com.yoonji.jwt.repository.UserRepository;
import com.yoonji.jwt.security.provider.JwtProvider;
import com.yoonji.jwt.security.user.UserAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public void logout(UserAdapter adapter) {
        RefreshToken savedRefreshToken = refreshTokenRepository.findByUser(adapter.getUser())
                .orElseThrow(() -> new RuntimeException("Refresh token not found for user"));

        savedRefreshToken.revoke();
    }


    @Transactional
    public TokenResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + request.getEmail()));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        // 토큰 생성
        String accessToken = jwtProvider.createAccessToken(user.getEmail());
        String refreshToken = jwtProvider.createRefreshToken(user.getEmail());


        RefreshToken newRefreshToken = refreshTokenRepository.findByUser(user)
                .map(oldRefreshToken -> oldRefreshToken.update(refreshToken, getExpireDate(), false))
                .orElseGet(() -> saveNewRefreshToken(user, refreshToken));

        return new TokenResponse(accessToken, refreshToken);
    }

    @Transactional
    public UserResponse signup(SignupRequest request) {
        checkDuplicateEmail(request.getEmail());

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(RoleType.ROLE_USER)
                .deleted(false)
                .passwordEncoder(passwordEncoder)
                .build();

        User savedUser = userRepository.save(user);

        return UserResponse.builder()
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .role(savedUser.getRole().name())
                .build();
    }

    private void checkDuplicateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("이미 사용중인 이메일");
        }
    }

    private RefreshToken saveNewRefreshToken(User user, String refreshToken) {
        return refreshTokenRepository.save(RefreshToken.builder()
                .token(refreshToken)
                .user(user)
                .expireDate(getExpireDate())
                .revoked(false)
                .build()
        );
    }

    private LocalDateTime getExpireDate() {
        return LocalDateTime.now().plusSeconds(jwtProvider.getRefreshTokenExpirationPeriod() / 1000L);
    }

}
