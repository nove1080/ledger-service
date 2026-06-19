package com.payment.ledger_service.ledger.service;

import com.payment.ledger_service.ledger.domain.DoubleEntryLedger;
import com.payment.ledger_service.ledger.domain.LedgerEventMessage;
import com.payment.ledger_service.ledger.domain.PaymentConfirmMessage;
import com.payment.ledger_service.ledger.repository.LedgerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LedgerService {

    private final DuplicateMessageFilter duplicateMessageFilter;
    private final LedgerRepository ledgerRepository;

    public LedgerEventMessage recordLedger(PaymentConfirmMessage message) {
        if (duplicateMessageFilter.isAlreadyProcessed(message)) {
            return createLedgerEventMessage(message);
        }

        DoubleEntryLedger doubleEntryLedger = DoubleEntryLedger.createForOrderConfirmation(message, ledgerRepository.findOrderConfirmationLedgers());
        ledgerRepository.saveLedgerTransaction(doubleEntryLedger);

        return createLedgerEventMessage(message);
    }

    private static LedgerEventMessage createLedgerEventMessage(PaymentConfirmMessage message) {
        return LedgerEventMessage.builder()
            .type(LedgerEventMessage.MessageType.LEDGER_RECORD_SUCCESS)
            .orderId(message.orderId())
            .build();
    }

}
