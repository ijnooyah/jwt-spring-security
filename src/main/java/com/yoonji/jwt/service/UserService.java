package com.yoonji.jwt.service;



import com.yoonji.jwt.dto.response.UserResponse;
import com.yoonji.jwt.security.user.UserAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Transactional(readOnly = true)
    public UserResponse getUser(UserAdapter adapter) {
        return UserResponse.builder()
                .name(adapter.getName())
                .email(adapter.getEmail())
                .role(adapter.getRole())
                .build();
    }

}
