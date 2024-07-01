package com.tech.picpay.challenge.service;

import com.tech.picpay.challenge.controller.dto.TransferDTO;
import com.tech.picpay.challenge.entity.Transaction;
import com.tech.picpay.challenge.entity.UserEntity;
import com.tech.picpay.challenge.entity.Wallet;
import com.tech.picpay.challenge.exception.InsufficientBalanceException;
import com.tech.picpay.challenge.exception.TransferToSameAccountException;
import com.tech.picpay.challenge.exception.UserNotFoundException;
import com.tech.picpay.challenge.repository.TransactionRepository;
import com.tech.picpay.challenge.repository.UserRepository;
import com.tech.picpay.challenge.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
public class TransactionService {
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;

    public TransactionService(UserRepository userRepository, TransactionRepository transactionRepository, WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
    }

    @Transactional
    public Transaction transfer(TransferDTO dto, long payer) {
        if (dto.payee() == payer) {
            throw new TransferToSameAccountException();
        }

        UserEntity payerDb = userRepository.findById(payer).orElseThrow(UserNotFoundException::new);
        UserEntity payeeDb = userRepository.findById(dto.payee()).orElseThrow(UserNotFoundException::new);
        Wallet walletPayer = payerDb.getWallet();

        if (!walletPayer.isBalancerEqualOrGreatherThan(dto.value())) {
            throw new InsufficientBalanceException();
        }

        Wallet walletPayee = payeeDb.getWallet();

        walletPayer.debit(dto.value());
        walletPayee.credit(dto.value());

        walletRepository.save(walletPayer);
        walletRepository.save(walletPayee);

        return transactionRepository.save(new Transaction(payerDb.getWallet(), walletPayee, dto.value()));
    }
}