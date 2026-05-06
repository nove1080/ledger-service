package com.payment.ledger_service.ledger.infrastructure.jpa.repository;

import com.payment.ledger_service.ledger.infrastructure.jpa.entity.JpaLedgerEntity;
import com.payment.ledger_service.ledger.infrastructure.jpa.entity.JpaLedgerTransactionEntity;
import com.payment.ledger_service.ledger.repository.LedgerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class JpaLedgerRepositoryAdapter implements LedgerRepository {

    private final JpaLedgerRepository jpaLedgerRepository;
    private final JpaLedgerTransactionRepository jpaLedgerTransactionRepository;

    @Override
    public boolean existsIdempotencyKey(String idempotencyKey) {
        return existsIdempotencyKey(idempotencyKey);
    }
}

interface JpaLedgerRepository extends JpaRepository<JpaLedgerEntity, Long> {

}

interface JpaLedgerTransactionRepository extends JpaRepository<JpaLedgerTransactionEntity, Long> {
    boolean existsIdempotencyKey(String idempotencyKey);
}
