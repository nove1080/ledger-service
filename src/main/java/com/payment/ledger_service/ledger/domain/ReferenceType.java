package com.payment.ledger_service.ledger.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ReferenceType {
    ORDER_ID("주문 식별자"),
    MEMBER_ID("회원 식별자");

    private final String description;
}
