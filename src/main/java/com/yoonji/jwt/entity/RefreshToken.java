package com.yoonji.jwt.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String token;

    private LocalDateTime expireDate;

    private boolean revoked;

    @Builder
    public RefreshToken(User user, String token, LocalDateTime expireDate, boolean revoked) {
        this.user = user;
        this.token = token;
        this.expireDate = expireDate;
        this.revoked = revoked;
    }

    public RefreshToken update(String newRefreshToken, LocalDateTime expireDate, boolean isRevoked) {
        this.token = newRefreshToken;
        this.expireDate = expireDate;
        this.revoked = isRevoked;
        return this;
    }

    public boolean isValid() {
        return this.getExpireDate().isAfter(LocalDateTime.now()) && !revoked;
    }

    public void revoke() {
        this.revoked = true;
    }

}
