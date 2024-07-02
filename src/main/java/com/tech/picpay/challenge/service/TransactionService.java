package com.tech.picpay.challenge.service;

import com.tech.picpay.challenge.client.DeviClient;
import com.tech.picpay.challenge.client.dto.SendNotificationDTO;
import com.tech.picpay.challenge.controller.dto.TransferDTO;
import com.tech.picpay.challenge.entity.Transaction;
import com.tech.picpay.challenge.entity.UserEntity;
import com.tech.picpay.challenge.entity.Wallet;
import com.tech.picpay.challenge.exception.InsufficientBalanceException;
import com.tech.picpay.challenge.exception.TransferToSameAccountException;
import com.tech.picpay.challenge.exception.TransferUnauthorizedException;
import com.tech.picpay.challenge.exception.UserNotFoundException;
import com.tech.picpay.challenge.listener.QueueSender;
import com.tech.picpay.challenge.repository.TransactionRepository;
import com.tech.picpay.challenge.repository.UserRepository;
import com.tech.picpay.challenge.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Currency;


@Service
public class TransactionService {
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final DeviClient deviClient;
    private final QueueSender queueSender;

    private final Currency currency = Currency.getInstance("BRL");
    private final DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

    public TransactionService(UserRepository userRepository, TransactionRepository transactionRepository, WalletRepository walletRepository, DeviClient deviClient, QueueSender queueSender) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
        this.deviClient = deviClient;
        this.queueSender = queueSender;
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

        try {
            var resp = deviClient.checkAuthorize();

            if (resp.getStatusCode().isError() || resp.getBody() == null || !resp.getBody().getAuthorization()) {
                throw new TransferUnauthorizedException();
            }

        } catch (Exception e) {
            throw new TransferUnauthorizedException();
        }

        queueSender.send(new SendNotificationDTO(dto.payee(), String.format("Você recebeu uma transferência no valor de %s", currency.getSymbol()+decimalFormat.format(dto.value()))));

        return transactionRepository.save(new Transaction(payerDb.getWallet(), walletPayee, dto.value()));
    }
}