package com.payment.ledger_service.ledger.domain;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record LedgerTransaction(
    Ledger ledger,
    BigDecimal amount,
    TransactionType transactionType,
    TransactionTypeExt transactionTypeExt,
    ReferenceType referenceType,
    String referenceId,
    String idempotencyKey
) {

}

