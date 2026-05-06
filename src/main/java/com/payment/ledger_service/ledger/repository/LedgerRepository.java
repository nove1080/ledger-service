package com.payment.ledger_service.ledger.repository;

public interface LedgerRepository {

    boolean existsIdempotencyKey(String idempotencyKey);
}
