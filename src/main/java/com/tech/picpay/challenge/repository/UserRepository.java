package com.tech.picpay.challenge.repository;

import com.tech.picpay.challenge.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByDocumentOrEmail(String document, String email);
    Optional<User> findByEmail(String email);
}
