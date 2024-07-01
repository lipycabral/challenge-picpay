package com.tech.picpay.challenge.controller;

import com.tech.picpay.challenge.controller.dto.TransferDTO;
import com.tech.picpay.challenge.entity.Transaction;
import com.tech.picpay.challenge.service.AuthenticationService;
import com.tech.picpay.challenge.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("transaction")
public class TransactionController {
    private final TransactionService transactionService;
    @Autowired
    private AuthenticationService authenticationService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("transfer")
    @PreAuthorize("hasAuthority('SCOPE_user')")
    public ResponseEntity<Transaction> transfer(Authentication authentication, @RequestBody @Valid TransferDTO dto) {
        return ResponseEntity.ok(transactionService.transfer(dto, Long.parseLong(authentication.getName())));
    }
}
