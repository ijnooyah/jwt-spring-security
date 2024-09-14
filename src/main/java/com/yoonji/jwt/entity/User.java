package com.yoonji.jwt.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    private boolean deleted;

    private LocalDateTime deletedAt;

    @Builder
    public User (String name, String email, String password, RoleType role, boolean deleted, PasswordEncoder passwordEncoder) {
        this.name = name;
        this.email = email;
        this.encodePassword(password, passwordEncoder);
        this.role = role;
        this.deleted = deleted;
    }

    private void encodePassword(String rawPassword, PasswordEncoder passwordEncoder) {
        if (rawPassword != null && !rawPassword.isEmpty()) {
            this.password = passwordEncoder.encode(rawPassword);
        }
    }
}
