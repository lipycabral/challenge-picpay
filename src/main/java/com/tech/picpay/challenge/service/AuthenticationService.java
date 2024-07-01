package com.tech.picpay.challenge.service;

import com.tech.picpay.challenge.controller.dto.AuthenticationUserDTO;

import com.tech.picpay.challenge.exception.AuthenticationException;
import com.tech.picpay.challenge.repository.UserRepository;
import com.tech.picpay.challenge.security.JwtService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private JwtService jwtService;
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public AuthenticationService(JwtService jwtService, UserRepository userRepository,
            BCryptPasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String authenticate(AuthenticationUserDTO dto) {
        var userDB = userRepository.findByEmail(dto.email());
        if (!userDB.isPresent()) {
            throw new AuthenticationException();
        }

        var user = userDB.get();

        var matches = passwordEncoder.matches(dto.password(), user.getPassword());
        if (!matches) {
            throw new AuthenticationException();
        }

        return jwtService.generateToken(user);
    }
}