package com.tech.picpay.challenge.service;

import com.tech.picpay.challenge.controller.dto.AuthenticationUserDTO;
import com.tech.picpay.challenge.exception.AuthenticationException;
import com.tech.picpay.challenge.repository.UserRepository;
import com.tech.picpay.challenge.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(JwtService jwtService, UserRepository userRepository,
                                 PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String authenticate(AuthenticationUserDTO dto) {
        var user = userRepository.findByEmail(dto.email()).orElseThrow(AuthenticationException::new);

        var matches = passwordEncoder.matches(dto.password(), user.getPassword());
        if (!matches) {
            throw new AuthenticationException();
        }

        return jwtService.generateToken(user);
    }
}