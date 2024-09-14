package com.yoonji.jwt.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
    private String email;
    private String name;
    private String role;

    @Builder
    public UserResponse(String email, String name, String role) {
        this.email = email;
        this.name = name;
        this.role = role;
    }
}
