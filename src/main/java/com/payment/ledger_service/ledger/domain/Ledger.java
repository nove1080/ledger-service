package com.payment.ledger_service.ledger.domain;

import lombok.Builder;

@Builder
public record Ledger(
    Long id,
    AccountType accountType,
    String name
) {

}
