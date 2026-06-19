package com.payment.ledger_service.ledger.domain;

import java.util.Map;
import lombok.Builder;

@Builder
public record OrderConfirmationLedgers(
    Ledger accountReceivable, //미수금
    Ledger pendingSettlement, //정산 대기금
    Ledger salesFee //수수료
) {

    public static OrderConfirmationLedgers from(Map<String, Ledger> ledgerMap) {
        return OrderConfirmationLedgers.builder()
            .accountReceivable(ledgerMap.get(LedgerName.ACCOUNTS_RECEIVABLE.getName()))
            .pendingSettlement(ledgerMap.get(LedgerName.PENDING_SETTLEMENT.getName()))
            .salesFee(ledgerMap.get(LedgerName.SALES_FEE.getName()))
            .build();
    }

}
