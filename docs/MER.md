# MER - Cadastro de Cliente, Conta Corrente e Conta Poupança

## Visão geral

```text
CLIENTE 1 ---- N CONTA 1 ---- N MOVIMENTACAO
```

## Entidades

### CLIENTE
| Campo | Tipo | Obrigatório | Observação |
|---|---|---:|---|
| id | UUID | Sim | Chave primária |
| nome | VARCHAR(150) | Sim | Nome completo do cliente |
| cpf | VARCHAR(11) | Sim | CPF único, somente números |
| email | VARCHAR(150) | Sim | E-mail único |
| telefone | VARCHAR(20) | Não | Telefone de contato |
| data_cadastro | TIMESTAMP | Sim | Data de cadastro |
| ativo | BOOLEAN | Sim | Indica se o cliente está ativo |

### CONTA
| Campo | Tipo | Obrigatório | Observação |
|---|---|---:|---|
| id | UUID | Sim | Chave primária |
| cliente_id | UUID | Sim | FK para cliente |
| agencia | VARCHAR(10) | Sim | Número da agência |
| numero | VARCHAR(20) | Sim | Número único da conta |
| tipo_conta | VARCHAR(20) | Sim | CORRENTE ou POUPANCA |
| saldo | NUMERIC(15,2) | Sim | Saldo atual da conta |
| limite | NUMERIC(15,2) | Sim | Limite especial, usado para conta corrente |
| data_abertura | TIMESTAMP | Sim | Data de abertura da conta |
| ativa | BOOLEAN | Sim | Indica se a conta está ativa |

### MOVIMENTACAO
| Campo | Tipo | Obrigatório | Observação |
|---|---|---:|---|
| id | UUID | Sim | Chave primária |
| conta_id | UUID | Sim | FK para conta |
| tipo_movimentacao | VARCHAR(20) | Sim | DEPOSITO ou RETIRADA |
| valor | NUMERIC(15,2) | Sim | Valor movimentado |
| saldo_anterior | NUMERIC(15,2) | Sim | Saldo antes da operação |
| saldo_atual | NUMERIC(15,2) | Sim | Saldo após a operação |
| descricao | VARCHAR(255) | Não | Descrição da movimentação |
| data_movimentacao | TIMESTAMP | Sim | Data/hora da movimentação |

## Regras iniciais sugeridas

- Cliente pode ter uma ou mais contas.
- Conta pode ser CORRENTE ou POUPANCA.
- Conta corrente pode ter limite.
- Conta poupança deve iniciar com limite igual a zero.
- Depósito sempre aumenta o saldo.
- Retirada reduz o saldo.
- Para fins didáticos, o SQL abaixo bloqueia saldo negativo.
- Caso queira permitir uso de limite em conta corrente, remova a constraint `ck_saldo` e controle a regra na aplicação.
