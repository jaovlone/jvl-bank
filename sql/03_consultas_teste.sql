-- Listar clientes com suas contas
SELECT
    c.id AS cliente_id,
    c.nome,
    c.cpf,
    ct.id AS conta_id,
    ct.agencia,
    ct.numero,
    ct.tipo_conta,
    ct.saldo,
    ct.limite,
    ct.ativa
FROM cliente c
JOIN conta ct ON ct.cliente_id = c.id
ORDER BY c.nome, ct.tipo_conta;

-- Consultar saldo por número da conta
SELECT
    ct.numero,
    ct.tipo_conta,
    ct.saldo,
    ct.limite,
    ct.ativa
FROM conta ct
WHERE ct.numero = '10001-1';

-- Extrato de uma conta
SELECT
    ct.numero,
    ct.tipo_conta,
    m.tipo_movimentacao,
    m.valor,
    m.saldo_anterior,
    m.saldo_atual,
    m.descricao,
    m.data_movimentacao
FROM movimentacao m
JOIN conta ct ON ct.id = m.conta_id
WHERE ct.numero = '10001-1'
ORDER BY m.data_movimentacao DESC;

-- Total de saldo por cliente
SELECT
    c.nome,
    SUM(ct.saldo) AS saldo_total
FROM cliente c
JOIN conta ct ON ct.cliente_id = c.id
GROUP BY c.nome
ORDER BY c.nome;
