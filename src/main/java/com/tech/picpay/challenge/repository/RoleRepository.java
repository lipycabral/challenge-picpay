package com.tech.picpay.challenge.repository;

import com.tech.picpay.challenge.entity.Role;
import com.tech.picpay.challenge.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}