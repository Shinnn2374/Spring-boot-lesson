package com.example.jwt_auth_example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "app_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String email;

     @ElementCollection(targetClass = RoleType.class, fetch = FetchType.EAGER)
     @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
     @Column(name = "roles", nullable = false)
     @Enumerated(EnumType.STRING)
     @Builder.Default
    private Set<RoleType> roles =  new HashSet<>();
}
