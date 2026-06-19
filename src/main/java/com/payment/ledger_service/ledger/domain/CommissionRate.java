package com.payment.ledger_service.ledger.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CommissionRate {
    DEFAULT(0.01);

    private final double rate;
}
