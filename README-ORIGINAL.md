# Sample Banco Cliente

Modelo simples para aprendizado com PostgreSQL e futura aplicação em Spring Boot Java.

## Objetivo

Simular uma aplicação bancária básica com:

- Cadastro de cliente
- Conta corrente
- Conta poupança
- Depósito
- Retirada
- Consulta de saldo
- Consulta de extrato

## Estrutura

```text
sample-banco-cliente/
├── README.md
├── docs/
│   └── MER.md
└── sql/
    ├── 01_create_schema.sql
    ├── 02_inserts_iniciais.sql
    ├── 03_consultas_teste.sql
    └── 04_simulacoes_deposito_retirada.sql
```

## Ordem para executar no PostgreSQL

Execute nesta ordem:

```sql
\i sql/01_create_schema.sql
\i sql/02_inserts_iniciais.sql
\i sql/03_consultas_teste.sql
```

Ou, se estiver no DBeaver, abra cada arquivo e execute na ordem:

1. `01_create_schema.sql`
2. `02_inserts_iniciais.sql`
3. `03_consultas_teste.sql`
4. `04_simulacoes_deposito_retirada.sql`, se quiser testar operações direto no banco

## Endpoints sugeridos para o Spring Boot

```text
POST   /clientes
GET    /clientes/{id}
GET    /clientes/{id}/contas

POST   /contas
GET    /contas/{id}
GET    /contas/{id}/saldo

POST   /contas/{id}/depositos
POST   /contas/{id}/retiradas
GET    /contas/{id}/movimentacoes
```

## Regras de negócio sugeridas

### Cliente

- CPF deve ser único.
- E-mail deve ser único.
- Cliente inativo não deve abrir nova conta.

### Conta

- Conta deve pertencer a um cliente existente.
- Conta pode ser `CORRENTE` ou `POUPANCA`.
- Conta poupança não possui limite.
- Conta corrente pode possuir limite.
- Conta inativa não deve aceitar depósito nem retirada.

### Depósito

- Valor deve ser maior que zero.
- Deve atualizar o saldo da conta.
- Deve gerar movimentação do tipo `DEPOSITO`.

### Retirada

- Valor deve ser maior que zero.
- Deve validar saldo disponível.
- Deve atualizar o saldo da conta.
- Deve gerar movimentação do tipo `RETIRADA`.

## Sugestão de modelagem Java

```text
br.com.seuprojeto.banco
├── cliente
│   ├── domain
│   ├── application
│   ├── infrastructure
│   └── api
├── conta
│   ├── domain
│   ├── application
│   ├── infrastructure
│   └── api
└── movimentacao
    ├── domain
    ├── application
    ├── infrastructure
    └── api
```

## Observação importante

Este modelo é propositalmente simples para aprendizado. Em uma aplicação bancária real, seria necessário tratar concorrência, auditoria, idempotência, trilha de eventos, autorização, autenticação, logs, antifraude, transações distribuídas e várias outras regras.
