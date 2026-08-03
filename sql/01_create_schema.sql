CREATE EXTENSION IF NOT EXISTS pgcrypto;

DROP TABLE IF EXISTS movimentacao;
DROP TABLE IF EXISTS conta;
DROP TABLE IF EXISTS cliente;

CREATE TABLE cliente (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(150) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    email VARCHAR(150) NOT NULL UNIQUE,
    telefone VARCHAR(20),
    CONSTRAINT ck_cliente_ativo CHECK (ativo IN ('0', '1'))
    data_cadastro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ativo CHAR(1) NOT NULL DEFAULT '1',);


CREATE TABLE conta (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    cliente_id UUID NOT NULL,
    agencia VARCHAR(10) NOT NULL,
    numero VARCHAR(20) NOT NULL UNIQUE,
    tipo_conta VARCHAR(20) NOT NULL,
    saldo NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    limite NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    data_abertura TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ativa BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT fk_conta_cliente
        FOREIGN KEY (cliente_id)
        REFERENCES cliente(id),

    CONSTRAINT ck_tipo_conta
        CHECK (tipo_conta IN ('CORRENTE', 'POUPANCA')),

    CONSTRAINT ck_saldo
        CHECK (saldo >= 0),

    CONSTRAINT ck_limite
        CHECK (limite >= 0),

    CONSTRAINT ck_limite_poupanca
        CHECK (
            tipo_conta <> 'POUPANCA'
            OR limite = 0
        )
);

CREATE TABLE movimentacao (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    conta_id UUID NOT NULL,
    tipo_movimentacao VARCHAR(20) NOT NULL,
    valor NUMERIC(15,2) NOT NULL,
    saldo_anterior NUMERIC(15,2) NOT NULL,
    saldo_atual NUMERIC(15,2) NOT NULL,
    descricao VARCHAR(255),
    data_movimentacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_movimentacao_conta
        FOREIGN KEY (conta_id)
        REFERENCES conta(id),

    CONSTRAINT ck_tipo_movimentacao
        CHECK (tipo_movimentacao IN ('DEPOSITO', 'RETIRADA')),

    CONSTRAINT ck_valor_movimentacao
        CHECK (valor > 0)
);

CREATE INDEX idx_conta_cliente_id ON conta(cliente_id);
CREATE INDEX idx_movimentacao_conta_id ON movimentacao(conta_id);
CREATE INDEX idx_movimentacao_data ON movimentacao(data_movimentacao);
