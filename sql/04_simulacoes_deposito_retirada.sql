-- Simulação didática de depósito em uma conta.
-- Conta usada: 10001-1

BEGIN;

WITH conta_atual AS (
    SELECT id, saldo
    FROM conta
    WHERE numero = '10001-1'
    FOR UPDATE
), atualizacao AS (
    UPDATE conta c
    SET saldo = c.saldo + 250.00
    FROM conta_atual ca
    WHERE c.id = ca.id
    RETURNING c.id, ca.saldo AS saldo_anterior, c.saldo AS saldo_atual
)
INSERT INTO movimentacao (
    conta_id,
    tipo_movimentacao,
    valor,
    saldo_anterior,
    saldo_atual,
    descricao
)
SELECT
    id,
    'DEPOSITO',
    250.00,
    saldo_anterior,
    saldo_atual,
    'Depósito de teste via SQL'
FROM atualizacao;

COMMIT;

-- Simulação didática de retirada em uma conta.
-- Conta usada: 10001-1
-- Neste modelo, saldo não pode ficar negativo por causa da constraint ck_saldo.

BEGIN;

WITH conta_atual AS (
    SELECT id, saldo
    FROM conta
    WHERE numero = '10001-1'
    FOR UPDATE
), atualizacao AS (
    UPDATE conta c
    SET saldo = c.saldo - 100.00
    FROM conta_atual ca
    WHERE c.id = ca.id
      AND c.saldo >= 100.00
    RETURNING c.id, ca.saldo AS saldo_anterior, c.saldo AS saldo_atual
)
INSERT INTO movimentacao (
    conta_id,
    tipo_movimentacao,
    valor,
    saldo_anterior,
    saldo_atual,
    descricao
)
SELECT
    id,
    'RETIRADA',
    100.00,
    saldo_anterior,
    saldo_atual,
    'Retirada de teste via SQL'
FROM atualizacao;

COMMIT;
