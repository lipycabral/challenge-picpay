package com.tech.picpay.challenge.controller;

import com.tech.picpay.challenge.controller.dto.AuthenticationUserDTO;
import com.tech.picpay.challenge.controller.dto.CreateUserDTO;
import com.tech.picpay.challenge.entity.User;
import com.tech.picpay.challenge.security.UserAuthenticated;
import com.tech.picpay.challenge.service.AuthenticationService;
import com.tech.picpay.challenge.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("authenticate")
    public ResponseEntity<String> authenticate(
            @RequestBody() AuthenticationUserDTO dto) {
        return ResponseEntity.ok(authenticationService.authenticate(dto));
    }

    @PostMapping("create-user")
    public ResponseEntity<User> createUser(@RequestBody() @Valid CreateUserDTO dto) {
        return ResponseEntity.ok(userService.createUser(dto));
    }

    @GetMapping
    public ResponseEntity<User> getUser(Authentication authentication) {
        return ResponseEntity.ok(userService.getUser(Long.valueOf(authentication.getName())));
    }
}
