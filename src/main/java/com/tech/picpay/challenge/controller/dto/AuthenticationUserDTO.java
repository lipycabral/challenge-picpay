package com.tech.picpay.challenge.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationUserDTO(
        @NotBlank String email,
        @NotBlank String password
) {
}
