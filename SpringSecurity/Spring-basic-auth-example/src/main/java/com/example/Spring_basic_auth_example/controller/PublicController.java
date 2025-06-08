package com.example.Spring_basic_auth_example.controller;

import com.example.Spring_basic_auth_example.entity.Role;
import com.example.Spring_basic_auth_example.entity.RoleType;
import com.example.Spring_basic_auth_example.entity.User;
import com.example.Spring_basic_auth_example.model.UserDto;
import com.example.Spring_basic_auth_example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/public")
public class PublicController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<String> getPublic() {
        return ResponseEntity.ok("Call public method");
    }

    @PostMapping("/account")
    public ResponseEntity<UserDto> createUserAccount(@RequestBody UserDto userDto, @RequestParam RoleType roleType) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createAccount(userDto,roleType));
    }

    private UserDto createAccount(UserDto userDto, RoleType roleType) {
        var user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());

        var createUser = userService.createNewAccount(user, Role.from(roleType));
        return userDto.builder()
                .username(createUser.getUsername())
                .password(createUser.getPassword())

                .build();
    }
}
