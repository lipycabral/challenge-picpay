package com.tech.picpay.challenge.service;

import com.tech.picpay.challenge.controller.dto.CreateUserDTO;
import com.tech.picpay.challenge.entity.UserEntity;
import com.tech.picpay.challenge.exception.UserAlreadyExistsException;
import com.tech.picpay.challenge.exception.UserNotFoundException;
import com.tech.picpay.challenge.repository.RoleRepository;
import com.tech.picpay.challenge.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserEntity createUser(CreateUserDTO dto) {
        Optional<UserEntity> userDB = userRepository.findByDocumentOrEmail(dto.document(), dto.email());
        if (userDB.isPresent()) {
            throw new UserAlreadyExistsException();
        }
        var role = roleRepository.findByName(dto.role().name());

        UserEntity user = dto.toUser();
        user.setRoles(Set.of(role));
        user.setPassword(passwordEncoder.encode(dto.password()));
        return userRepository.save(user);
    }

    public UserEntity getUser(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }
}
