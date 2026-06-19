package com.payment.ledger_service.ledger.infrastructure.jpa.entity;

import com.payment.ledger_service.common.domain.BaseTimeEntity;
import com.payment.ledger_service.ledger.domain.ReferenceType;
import com.payment.ledger_service.ledger.domain.TransactionType;
import com.payment.ledger_service.ledger.domain.TransactionTypeExt;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString(callSuper = true)
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "ledger_transaction")
public class JpaLedgerTransactionEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ledger_transaction_id", updatable = false)
    private Long id;

    @Column(nullable = false)
    private Long ledgerId;

    @Column(nullable = false)
    private String idempotencyKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type_code", nullable = false)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type_ext_code", nullable = false)
    private TransactionTypeExt transactionTypeExt;

    @Enumerated(EnumType.STRING)
    private ReferenceType referenceType;

    private String referenceId;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;
}

