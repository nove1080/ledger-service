package com.payment.ledger_service.ledger.infrastructure.jpa.mapper;

import com.payment.ledger_service.ledger.domain.LedgerTransaction;
import com.payment.ledger_service.ledger.infrastructure.jpa.entity.JpaLedgerTransactionEntity;

public abstract class JpaLedgerTransactionMapper {

    public static JpaLedgerTransactionEntity mapToEntity(LedgerTransaction ledgerTransaction) {
        return JpaLedgerTransactionEntity.builder()
            .ledgerId(ledgerTransaction.ledger().id())
            .referenceId(ledgerTransaction.referenceId())
            .referenceType(ledgerTransaction.referenceType())
            .transactionType(ledgerTransaction.transactionType())
            .transactionTypeExt(ledgerTransaction.transactionTypeExt())
            .amount(ledgerTransaction.amount())
            .idempotencyKey(ledgerTransaction.idempotencyKey())
            .build();
    }

}
