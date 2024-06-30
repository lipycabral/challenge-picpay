package com.tech.picpay.challenge.controller.dto;

import com.tech.picpay.challenge.entity.Role;
import com.tech.picpay.challenge.entity.User;
import com.tech.picpay.challenge.entity.Wallet;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserDTO(
        @NotBlank String email,
        @NotBlank String name,
        @NotBlank String password,
        @NotBlank String document,
        @NotNull Role.Enum role
) {

    public User toUser() {
        User user = new User(name, document, password, email);
        Wallet wallet = new Wallet();
        user.setWallet(wallet);
        wallet.setUser(user);
        return user;
    }
}
