package com.tech.picpay.challenge.controller.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransferDTO(@DecimalMin("0.01") BigDecimal value, @NotNull long payee) {
}
