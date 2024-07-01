package com.tech.picpay.challenge.repository;

import com.tech.picpay.challenge.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}