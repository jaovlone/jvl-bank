# 🏦 JVL Bank

Sistema bancário desenvolvido com Java 21 e Spring Boot 3, seguindo boas práticas de arquitetura em camadas, testes automatizados, Docker e documentação OpenAPI.

Badges

Java 21
Spring Boot
Docker
PostgreSQL
GitHub Actions
Qodana
License

## ✨ Funcionalidades

- Cadastro de clientes
- Atualização de clientes
- Inativação lógica de clientes
- Abertura de contas
- Depósitos
- Saques
- Transferências entre contas
- Histórico de movimentações
- Consulta de extrato
- Documentação Swagger
- Testes automatizados

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

Estrutura

src
 ├── cliente
 ├── conta
 ├── movimentacao
 ├── config
 └── resources

## 🚀 Tecnologias

- Java 21
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Hibernate
- Maven
- Docker
- Docker Compose
- Flyway
- OpenAPI / Swagger
- JUnit 5
- Mockito
- GitHub Actions
- Qodana

⚙️ Como Executar

git clone ...

cd JVL-Bank

mvn clean install

mvn spring-boot:run

🐳 Docker

docker compose up --build

📖 Swagger

http://localhost:8080/swagger-ui/index.html

🧪 Testes

mvn clean test

📷 Prints

📌 Próximos Passos
- JWT
- Spring Security
- Redis
- RabbitMQ
- Kubernetes
- Deploy AWS

👨‍💻 Autor
João Victor Braga Santos

GitHub

LinkedIn
