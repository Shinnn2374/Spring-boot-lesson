package com.example.spring_basic_auth_example.repository;

import com.example.spring_basic_auth_example.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {"roles "})
    Optional<User> findByUsername(String username);
}
