package com.chaTop.Backend.repository;

import com.chaTop.Backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String name);

    Boolean existsByName(String name);

    Boolean existsByEmail(String email);
}
