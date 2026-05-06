INSERT INTO ledger (name, account_type_code, created_at) VALUES
    ('house cash', 'ASSET', NOW()),             #실제 현금 - PG사나 은행을 통해 우리 계좌에 들어온 실제 현금
    ('pending settlement', 'LIABILITY', NOW()), #정산 대기금 - 판매자에게 나중에 줘야할 금액
    ('sales fee', 'REVENUE', NOW()),            #결제 수수료 수익 - 플랫폼이 거래 중개 대가로 가져가는 수익
    ('discount expense', 'EXPENSE', NOW()),     #할인 비용 - 쿠폰 발행이나 할인 이벤트로 인해 플랫폼이 부담한 금액
    ('accounts receivable', 'ASSET', NOW());    #미수금 - 결제는 완료되었으나 아직 PG사로부터 입금되지 않은 채권
