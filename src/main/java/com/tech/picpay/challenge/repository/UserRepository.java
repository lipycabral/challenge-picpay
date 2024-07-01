package com.tech.picpay.challenge.repository;

import com.tech.picpay.challenge.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByDocumentOrEmail(String document, String email);

    Optional<UserEntity> findByEmail(String email);
}
