package com.yoonji.jwt.controller;


import com.yoonji.jwt.dto.response.UserResponse;
import com.yoonji.jwt.security.user.UserAdapter;
import com.yoonji.jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<UserResponse> getUser(@AuthenticationPrincipal UserAdapter adapter) {
        UserResponse userResponse = userService.getUser(adapter);
        return ResponseEntity.ok(userResponse);
    }

}
