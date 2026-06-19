package com.payment.ledger_service.ledger.repository;

import com.payment.ledger_service.ledger.domain.DoubleEntryLedger;
import com.payment.ledger_service.ledger.domain.OrderConfirmationLedgers;

public interface LedgerRepository {

    boolean existsIdempotencyKey(String idempotencyKey);

    OrderConfirmationLedgers findOrderConfirmationLedgers();

    void saveLedgerTransaction(DoubleEntryLedger doubleEntryLedger);
}
