package com.example.spring_reactive_auth_example.service;

import com.example.spring_reactive_auth_example.entity.Role;
import com.example.spring_reactive_auth_example.entity.User;
import com.example.spring_reactive_auth_example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public User createNewUser(User user, Role role) {
        user.setRoles(Collections.singletonList(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        role.setUser(user);
        return userRepository.save(user);
    }

     public User findByUsername(String username) {
         return userRepository.findByUsername(username)
                 .orElseThrow(() -> new RuntimeException("Username not found"));
     }
}
