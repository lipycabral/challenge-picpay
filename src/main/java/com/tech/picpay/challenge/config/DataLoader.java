package com.tech.picpay.challenge.config;

import com.tech.picpay.challenge.entity.Role;
import com.tech.picpay.challenge.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Arrays.stream(Role.Enum.values()).forEach(role -> roleRepository.save(role.get()));
    }

}
