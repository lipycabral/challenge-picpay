package com.tech.picpay.challenge.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "tb_transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "wallet_origin_id", nullable = false)
    private Wallet origin;

    @ManyToOne
    @JoinColumn(name = "wallet_destination_id", nullable = false)
    private Wallet destination;

    @Column(name = "value", nullable = false)
    private BigDecimal value;

    public Transaction() {
    }

    public Transaction(Wallet origin, Wallet destination, BigDecimal value) {
        this.origin = origin;
        this.destination = destination;
        this.value = value;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Wallet getOrigin() {
        return origin;
    }

    public void setOrigin(Wallet origin) {
        this.origin = origin;
    }

    public Wallet getDestination() {
        return destination;
    }

    public void setDestination(Wallet destination) {
        this.destination = destination;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
