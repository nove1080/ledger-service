package com.payment.ledger_service.ledger.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LedgerName {
    HOUSE_CASH("house cash"),
    PENDING_SETTLEMENT("pending settlement"),
    SALES_FEE("sales fee"),
    DISCOUNT_EXPENSE("discount expense"),
    ACCOUNTS_RECEIVABLE("accounts receivable");

    private final String name;
}
