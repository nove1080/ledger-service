package com.payment.ledger_service.ledger.domain;

import lombok.Builder;

@Builder
public record PaymentOrder(
    Long sellerId,
    Long productId,
    Long amount
) {

}
