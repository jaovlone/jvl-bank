# 🏦 JVL Bank

Sistema bancário desenvolvido em **Java 21** utilizando **Spring Boot 3**, com arquitetura em camadas, testes automatizados, documentação OpenAPI/Swagger e execução via Docker.

O projeto foi desenvolvido com foco em boas práticas de desenvolvimento backend, organização de código, regras de negócio e arquitetura utilizada em aplicações Java modernas.

---

# 🚀 Tecnologias

- ☕ Java 21
- 🍃 Spring Boot 3
- 📦 Maven
- 🐘 PostgreSQL
- 🗃 Spring Data JPA / Hibernate
- 📚 OpenAPI / Swagger
- ✅ Bean Validation
- 🐳 Docker
- 🧪 JUnit 5
- 🎭 Mockito
- 🌿 Git / GitHub
- ⚙️ GitHub Actions

---

# ✨ Funcionalidades

## 👤 Clientes

- Cadastro de clientes
- Consulta por ID
- Atualização de clientes
- Inativação lógica
- Listagem de clientes ativos

---

## 🏦 Contas

- Abertura de conta corrente
- Abertura de conta poupança
- Consulta por ID
- Listagem de contas
- Listagem de contas por cliente
- Inativação de conta

---

## 💰 Operações Bancárias

- Depósito
- Saque
- Transferência entre contas
- Atualização automática do saldo

---

## 📄 Extrato

- Histórico completo das movimentações
- Consulta por conta

---

# 🏗 Arquitetura

O projeto segue uma arquitetura em camadas.

```
                Controller
                     │
                     ▼
                 Service
                     │
                     ▼
               Repository
                     │
                     ▼
               PostgreSQL
```

Cada responsabilidade está separada em sua própria camada, facilitando manutenção, testes e evolução do sistema.

---

# 📦 Estrutura do Projeto

```
src
└── main
    ├── java
    │
    ├── cliente
    │   ├── controller
    │   ├── domain
    │   ├── dto
    │   ├── mapper
    │   ├── repository
    │   └── service
    │
    ├── conta
    │   ├── controller
    │   ├── domain
    │   ├── dto
    │   ├── mapper
    │   ├── repository
    │   └── service
    │
    ├── movimentacao
    │   ├── controller
    │   ├── domain
    │   ├── dto
    │   ├── mapper
    │   ├── repository
    │   └── service
    │
    └── config

resources
```

---

# 📚 Modelo de Domínio

```
Cliente
    │
    ├───────────┐
    │           │
Conta       Conta
    │
    │
Movimentacao
```

Cada cliente pode possuir várias contas.

Cada conta possui várias movimentações.

---

# 🧪 Testes

O projeto possui testes automatizados utilizando:

- JUnit 5
- Mockito

Cobertura atual:

- Regras de negócio da Conta
- Serviços da Conta

Execute:

```bash
mvn clean test
```

---

# 🐳 Executando com Docker

## Build

```bash
docker compose up --build
```

Após iniciar:

API

```
http://localhost:8080
```

Swagger

```
http://localhost:8080/swagger-ui/index.html
```

---

# 💻 Executando Localmente

## Clonar

```bash
git clone https://github.com/jaovlone/sistema_banco_v0.1.git
```

## Entrar na pasta

```bash
cd sistema_banco_v0.1
```

## Executar

```bash
mvn clean spring-boot:run
```

---

# 📖 Documentação da API

A documentação está disponível através do Swagger.

```
http://localhost:8080/swagger-ui/index.html
```

Também é possível acessar o OpenAPI JSON:

```
http://localhost:8080/v3/api-docs
```

---

# 🔄 Fluxo de uma Operação

```
Cliente

↓

Controller

↓

Service

↓

Repository

↓

Banco

↓

Repository

↓

Service

↓

Controller

↓

Resposta JSON
```

---

# 📌 Exemplos de Funcionalidades

### Criar Cliente

```
POST /api/clientes
```

### Abrir Conta

```
POST /api/contas
```

### Depositar

```
POST /api/contas/{id}/depositos
```

### Sacar

```
POST /api/contas/{id}/saques
```

### Transferir

```
POST /api/contas/{id}/transferencias
```

### Consultar Extrato

```
GET /api/movimentacoes/conta/{id}
```

---

# 📈 Próximas Melhorias

- Autenticação JWT
- Spring Security
- Redis
- RabbitMQ
- Kubernetes
- Deploy em Cloud
- Testes de integração
- Testcontainers
- Observabilidade (Actuator + Micrometer)

---

# 👨‍💻 Autor

**João Victor Braga Santos**

GitHub:

https://github.com/jaovlone

LinkedIn:

https://www.linkedin.com/in/jo%C3%A3o-victor-braga-santos-08446a212/

---

# ⭐ Objetivo

Este projeto foi desenvolvido com o objetivo de consolidar conhecimentos em desenvolvimento backend utilizando Java e Spring Boot, aplicando boas práticas de arquitetura, testes automatizados, persistência de dados e conteinerização com Docker.
