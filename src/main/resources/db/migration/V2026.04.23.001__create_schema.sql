CREATE TABLE transaction_type
(
    code VARCHAR(255) PRIMARY KEY COMMENT '거래 유형 코드',
    name VARCHAR(255) NOT NULL COMMENT '거래 유형 이름'
) COMMENT '복식부기 거래 유형 정의 테이블 - CREDIT, DEBIT';

CREATE TABLE transaction_type_ext
(
    code VARCHAR(255) PRIMARY KEY COMMENT '거래 유형 코드 - ex) OO',
    name VARCHAR(255) NOT NULL COMMENT '거래 유형 이름 - ex) Order'
) COMMENT '복식부기 거래 유형 확장 테이블';

CREATE TABLE account_type
(
    code VARCHAR(255) PRIMARY KEY COMMENT '계좌 유형 코드 - ex) AA, LA',
    name VARCHAR(255) NOT NULL COMMENT '계좌 유형 이름 - ex) Asset, Liability'
) COMMENT '계좌 유형 정의 테이블';

CREATE TABLE ledger (
    ledger_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL COMMENT '원장 이름',
    account_type_code VARCHAR(255) NOT NULL COMMENT '계좌 유형 코드',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_ledger_account_type_code FOREIGN KEY (account_type_code) REFERENCES account_type (code)
) COMMENT '원장';

CREATE TABLE ledger_transaction
(
    ledger_id BIGINT NOT NULL,
    idempotency_key VARCHAR(255) NOT NULL,
    transaction_type_code VARCHAR(255) NOT NULL,
    transaction_type_ext_code VARCHAR(255) NOT NULL,
    reference_type VARCHAR(255) COMMENT '참조된 엔티티의 유형 - ex) Order',
    reference_id VARCHAR(255) COMMENT '참조된 엔티티의 ID - ex) 주문 식별자',
    amount DECIMAL(19, 2),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_ledger_transaction_ledger_id FOREIGN KEY (ledger_id) REFERENCES ledger (ledger_id),
    CONSTRAINT fk_ledger_transaction_transaction_type_code FOREIGN KEY (transaction_type_code) REFERENCES transaction_type (code),
    CONSTRAINT fk_ledger_transaction_transaction_type_ext_code FOREIGN KEY (transaction_type_ext_code) REFERENCES transaction_type_ext (code)
) COMMENT '원장 거래 테이블 - 거래 유형과 금액을 기록하여 회계 처리를 위한 테이블';
