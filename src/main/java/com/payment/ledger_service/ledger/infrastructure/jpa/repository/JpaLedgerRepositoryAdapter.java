package com.payment.ledger_service.ledger.infrastructure.jpa.repository;

import com.payment.ledger_service.ledger.domain.DoubleEntryLedger;
import com.payment.ledger_service.ledger.domain.Ledger;
import com.payment.ledger_service.ledger.domain.LedgerName;
import com.payment.ledger_service.ledger.domain.OrderConfirmationLedgers;
import com.payment.ledger_service.ledger.infrastructure.jpa.entity.JpaLedgerEntity;
import com.payment.ledger_service.ledger.infrastructure.jpa.entity.JpaLedgerTransactionEntity;
import com.payment.ledger_service.ledger.infrastructure.jpa.mapper.JpaLedgerTransactionMapper;
import com.payment.ledger_service.ledger.repository.LedgerRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
public class JpaLedgerRepositoryAdapter implements LedgerRepository {

    private final JpaLedgerRepository jpaLedgerRepository;
    private final JpaLedgerTransactionRepository jpaLedgerTransactionRepository;

    @Override
    @Transactional(readOnly = true)
    public boolean existsIdempotencyKey(String idempotencyKey) {
        return jpaLedgerTransactionRepository.existsByIdempotencyKey(idempotencyKey);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderConfirmationLedgers findOrderConfirmationLedgers() {
        Map<String, Ledger> ledgerMap = jpaLedgerRepository.findByNameIn(List.of(
                LedgerName.PENDING_SETTLEMENT.getName(),
                LedgerName.SALES_FEE.getName(),
                LedgerName.ACCOUNTS_RECEIVABLE.getName()
            ))
            .stream()
            .collect(Collectors.toMap(
                JpaLedgerEntity::getName,
                entity -> new Ledger(entity.getId(), entity.getAccountType(), entity.getName())
            ));

        return OrderConfirmationLedgers.from(ledgerMap);
    }

    @Override
    @Transactional
    public void saveLedgerTransaction(DoubleEntryLedger ledger) {
        List<JpaLedgerTransactionEntity> entities = ledger.transactions().stream()
            .map(JpaLedgerTransactionMapper::mapToEntity)
            .toList();

        jpaLedgerTransactionRepository.saveAll(entities);
    }
}

interface JpaLedgerRepository extends JpaRepository<JpaLedgerEntity, Long> {
    List<JpaLedgerEntity> findByNameIn(List<String> names);
}

interface JpaLedgerTransactionRepository extends JpaRepository<JpaLedgerTransactionEntity, Long> {
    boolean existsByIdempotencyKey(String idempotencyKey);
}
