package com.tech.picpay.challenge.repository;

import com.tech.picpay.challenge.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
