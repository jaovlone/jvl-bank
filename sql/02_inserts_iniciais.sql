INSERT INTO cliente (
    id, nome, cpf, email, telefone
) VALUES
(
    '11111111-1111-1111-1111-111111111111',
    'João Silva',
    '12345678901',
    'joao@email.com',
    '61999990001'
),
(
    '22222222-2222-2222-2222-222222222222',
    'Maria Souza',
    '98765432100',
    'maria@email.com',
    '61999990002'
),
(
    '33333333-3333-3333-3333-333333333333',
    'Pedro Almeida',
    '45678912300',
    'pedro@email.com',
    '61999990003'
);

INSERT INTO conta (
    id, cliente_id, agencia, numero, tipo_conta, saldo, limite
) VALUES
(
    'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
    '11111111-1111-1111-1111-111111111111',
    '0001',
    '10001-1',
    'CORRENTE',
    1500.00,
    500.00
),
(
    'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
    '11111111-1111-1111-1111-111111111111',
    '0001',
    '20001-1',
    'POUPANCA',
    3000.00,
    0.00
),
(
    'cccccccc-cccc-cccc-cccc-cccccccccccc',
    '22222222-2222-2222-2222-222222222222',
    '0001',
    '10002-1',
    'CORRENTE',
    800.00,
    300.00
),
(
    '99999999-9999-9999-9999-999999999999',
    '33333333-3333-3333-3333-333333333333',
    '0001',
    '20002-1',
    'POUPANCA',
    1200.00,
    0.00
);

INSERT INTO movimentacao (
    id, conta_id, tipo_movimentacao, valor, saldo_anterior, saldo_atual, descricao
) VALUES
(
    'dddddddd-dddd-dddd-dddd-dddddddddddd',
    'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
    'DEPOSITO',
    500.00,
    1000.00,
    1500.00,
    'Depósito inicial em conta corrente'
),
(
    'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee',
    'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
    'DEPOSITO',
    3000.00,
    0.00,
    3000.00,
    'Aplicação inicial na poupança'
),
(
    'ffffffff-ffff-ffff-ffff-ffffffffffff',
    'cccccccc-cccc-cccc-cccc-cccccccccccc',
    'DEPOSITO',
    800.00,
    0.00,
    800.00,
    'Depósito inicial em conta corrente'
),
(
    '77777777-7777-7777-7777-777777777777',
    '99999999-9999-9999-9999-999999999999',
    'DEPOSITO',
    1200.00,
    0.00,
    1200.00,
    'Aplicação inicial na poupança'
);
