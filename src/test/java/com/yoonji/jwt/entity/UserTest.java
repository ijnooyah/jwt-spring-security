package com.yoonji.jwt.entity;

import com.yoonji.jwt.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class UserTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void testMember() throws Exception {
//        User user = new User("하윤지", "ijnooyah@gmail.com");
//        User savedUser = userRepository.save(user);
//
//        User findUser = userRepository.findById(savedUser.getId())
//                .orElseThrow(() -> new IllegalArgumentException("Member not found with id: " + savedUser.getId()));
//
//        assertThat(findUser.getId()).isEqualTo(user.getId());
//        assertThat(findUser.getName()).isEqualTo(user.getName());
//        assertThat(findUser.getEmail()).isEqualTo(user.getEmail());
//
//        assertThat(findUser).isEqualTo(user);
    }
}