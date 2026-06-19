package com.payment.ledger_service.ledger.domain;

import lombok.Builder;

@Builder
public record LedgerEventMessage (
    MessageType type,
    String orderId
){

    public enum MessageType {
        LEDGER_RECORD_SUCCESS;

        public String getTopicName() {
            return this.name().toLowerCase().replace("_", "-");
        }
    }

}
