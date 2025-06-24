package com.example.jwt_auth_example.security;

import com.example.jwt_auth_example.entity.User;
import com.example.jwt_auth_example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername( username)
                .orElseThrow(() -> new UsernameNotFoundException(MessageFormat.format("User with name: {0} not found", username)));
        return new AppUserDetails(user);
    }
}
