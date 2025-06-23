package com.example.Spring_Security.service;

import com.example.Spring_Security.entity.Role;
import com.example.Spring_Security.entity.User;
import com.example.Spring_Security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public User createNewAccount(User user, Role role) {
         user.setRoles(Collections.singletonList(role));
         user.setPassword(passwordEncoder.encode(user.getPassword()));
         return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }


}
