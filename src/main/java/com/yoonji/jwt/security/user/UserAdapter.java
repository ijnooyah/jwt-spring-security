package com.yoonji.jwt.security.user;

import com.yoonji.jwt.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserAdapter extends CustomUserDetails {
    private String email;
    private String password;
    private String role;
    private String name;

    public UserAdapter(User user) {
        super(user);
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getRole().name();
        this.name = user.getName();
    }
}
