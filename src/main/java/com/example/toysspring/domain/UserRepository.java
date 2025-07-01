package com.example.toysspring.domain;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    List<User> findAll();
    Optional<User> findByUserId(Long userId);
    Optional<User> findByUserEmail(String email);
}
