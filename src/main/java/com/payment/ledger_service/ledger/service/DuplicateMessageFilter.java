package com.payment.ledger_service.ledger.service;

import com.payment.ledger_service.common.util.IdempotencyKeyGenerator;
import com.payment.ledger_service.ledger.domain.PaymentConfirmMessage;
import com.payment.ledger_service.ledger.repository.LedgerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DuplicateMessageFilter {

    private final LedgerRepository ledgerRepository;

    public boolean isAlreadyProcessed(PaymentConfirmMessage message) {
        String idempotencyKey = IdempotencyKeyGenerator.generate(message);
        return ledgerRepository.existsIdempotencyKey(idempotencyKey);
    }

}
