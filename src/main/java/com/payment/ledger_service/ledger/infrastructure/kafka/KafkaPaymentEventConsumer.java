package com.payment.ledger_service.ledger.infrastructure.kafka;

import com.payment.ledger_service.ledger.domain.LedgerEventMessage;
import com.payment.ledger_service.ledger.domain.PaymentConfirmMessage;
import com.payment.ledger_service.ledger.domain.PaymentConfirmMessage.MessageType;
import com.payment.ledger_service.ledger.service.LedgerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaPaymentEventConsumer {
    private final LedgerService ledgerService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(
        topics = MessageType.PAYMENT_CONFIRM_SUCCESS,
        groupId = "${spring.kafka.consumer.group-id}",
        containerFactory = "paymentConfirmKafkaListenerContainerFactory"
    )
    public void consumePaymentConfirmMessage(PaymentConfirmMessage message) {
        log.info("Received topic: {}, message: {}", MessageType.PAYMENT_CONFIRM_SUCCESS, message);
        LedgerEventMessage ledgerEventMessage = ledgerService.recordLedger(message);
        kafkaTemplate.send(LedgerEventMessage.MessageType.LEDGER_RECORD_SUCCESS.getTopicName(), ledgerEventMessage);
    }
}
