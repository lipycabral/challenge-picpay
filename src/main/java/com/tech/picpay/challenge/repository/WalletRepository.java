package com.tech.picpay.challenge.repository;

import com.tech.picpay.challenge.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
