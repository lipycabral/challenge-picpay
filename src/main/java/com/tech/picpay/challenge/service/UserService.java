package com.tech.picpay.challenge.service;

import com.tech.picpay.challenge.controller.dto.CreateUserDTO;
import com.tech.picpay.challenge.entity.User;
import com.tech.picpay.challenge.exception.UserAlreadyExistsException;
import com.tech.picpay.challenge.exception.UserNotFoundException;
import com.tech.picpay.challenge.repository.RoleRepository;
import com.tech.picpay.challenge.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(CreateUserDTO dto) {
        Optional<User> userDB = userRepository.findByDocumentOrEmail(dto.document(), dto.email());
        if (userDB.isPresent()) {
            throw new UserAlreadyExistsException();
        }
        var role = roleRepository.findByName(dto.role().name());

        User user = dto.toUser();
        user.setRoles(Set.of(role));
        user.setPassword(passwordEncoder.encode(dto.password()));
        return userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }
}
