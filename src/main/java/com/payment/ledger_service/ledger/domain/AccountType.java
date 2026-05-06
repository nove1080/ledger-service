package com.payment.ledger_service.ledger.domain;

import lombok.Getter;

@Getter
public enum AccountType {
    ASSET("자산"),
    LIABILITY("부채"),
    EQUITY("자본"),
    REVENUE("수익"),
    EXPENSE("비용");

    private final String description;

    AccountType(String description) {
        this.description = description;
    }
}
