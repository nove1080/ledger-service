package com.payment.ledger_service.ledger.domain;

import com.payment.ledger_service.common.util.IdempotencyKeyGenerator;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public record DoubleEntryLedger(
    List<LedgerTransaction> transactions
) {

    public DoubleEntryLedger {
        validateSize(transactions);
        validateBalance(transactions);
    }

    public static DoubleEntryLedger createForOrderConfirmation(PaymentConfirmMessage message, OrderConfirmationLedgers ledgers) {
        String idempotencyKey = IdempotencyKeyGenerator.generate(message);
        List<LedgerTransaction> transactions = new ArrayList<>();

        // 1. 차변: 미수금 (자산 증가)
        // referenceId: orderId, referenceType: ORDER_ID
        transactions.add(LedgerTransaction.builder()
            .ledger(ledgers.accountReceivable())
            .amount(BigDecimal.valueOf(message.amount()))
            .transactionType(TransactionType.DEBIT)
            .transactionTypeExt(TransactionTypeExt.ORDER_CONFIRMATION)
            .referenceType(ReferenceType.ORDER_ID)
            .referenceId(message.orderId())
            .idempotencyKey(idempotencyKey)
            .build());

        // 2. 대변: 판매자별 정산 대기금 및 수수료
        for (PaymentOrder order : message.paymentOrders()) {
            BigDecimal totalOrderAmount = BigDecimal.valueOf(order.amount());
            BigDecimal feeAmount = totalOrderAmount.multiply(BigDecimal.valueOf(CommissionRate.DEFAULT.getRate()));
            BigDecimal settlementAmount = totalOrderAmount.subtract(feeAmount);

            // 대변: 정산 대기금 (부채 증가)
            // referenceId: memberId, referenceType: MEMBER_ID
            transactions.add(LedgerTransaction.builder()
                .ledger(ledgers.pendingSettlement())
                .amount(settlementAmount)
                .transactionType(TransactionType.CREDIT)
                .transactionTypeExt(TransactionTypeExt.ORDER_CONFIRMATION)
                .referenceType(ReferenceType.MEMBER_ID)
                .referenceId(order.sellerId().toString())
                .idempotencyKey(idempotencyKey)
                .build());

            // 대변: 수수료 수익 (수익 발생)
            // referenceId: orderId, referenceType: ORDER_ID
            transactions.add(LedgerTransaction.builder()
                .ledger(ledgers.salesFee())
                .amount(feeAmount)
                .transactionType(TransactionType.CREDIT)
                .transactionTypeExt(TransactionTypeExt.ORDER_CONFIRMATION)
                .referenceType(ReferenceType.ORDER_ID)
                .referenceId(message.orderId())
                .idempotencyKey(idempotencyKey)
                .build());
        }

        return new DoubleEntryLedger(transactions);
    }

    private static void validateSize(List<LedgerTransaction> transactions) {
        if (transactions.size() < 2) {
            throw new IllegalStateException("복식부기를 위해서는 최소 2개 이상의 거래가 필요합니다.");
        }
    }

    private static void validateBalance(List<LedgerTransaction> transactions) {
        BigDecimal debitSum = sumBy(transactions, TransactionType.DEBIT);
        BigDecimal creditSum = sumBy(transactions, TransactionType.CREDIT);

        if (debitSum.compareTo(creditSum) != 0) {
            throw new IllegalStateException("차변과 대변의 합이 일치하지 않습니다. 현재 차변 합계: " + debitSum + ", 대변 합계: " + creditSum);
        }
    }

    /**
     * 주어진 거래 리스트에서 특정 거래 유형에 해당하는 금액의 합계를 계산합니다.
     * @param transactions 거래 리스트
     * @param type 계산할 거래 유형 (DEBIT 또는 CREDIT)
     * @return 금액의 합계
     */
    private static BigDecimal sumBy(List<LedgerTransaction> transactions, TransactionType type) {
        return transactions.stream()
            .filter(t -> t.transactionType() == type)
            .map(LedgerTransaction::amount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
