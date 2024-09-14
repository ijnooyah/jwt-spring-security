package com.yoonji.jwt.repository;

import com.yoonji.jwt.entity.RefreshToken;
import com.yoonji.jwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUser(User user);

    @Query("SELECT r FROM RefreshToken r WHERE r.user.email = :email")
    Optional<RefreshToken> findByUserEmail(String email);
}
