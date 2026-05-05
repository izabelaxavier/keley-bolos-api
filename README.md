# 🍰 Keley Bolos API

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java" />
  <img src="https://img.shields.io/badge/Spring%20Boot-3-green?style=for-the-badge&logo=spring-boot" />
  <img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white" />
  <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white" />
  <img src="https://img.shields.io/badge/Railway-0B0D0E?style=for-the-badge&logo=railway&logoColor=white" />
</p>

> 🚧 **Status:** Projeto concluído (com deploy realizado anteriormente em ambiente cloud)

---

## 📌 Visão Geral

A **Keley Bolos API** é um sistema backend desenvolvido para a gestão de uma confeitaria real da família.

O objetivo do projeto foi automatizar o fluxo de pedidos, controle financeiro e geração de comprovantes, substituindo processos manuais por uma API estruturada utilizando Spring Boot.

O sistema foi construído com foco em boas práticas de desenvolvimento backend, arquitetura em camadas e simulação de ambiente real de produção.

---

## 🏗️ Arquitetura do Sistema

O projeto segue uma arquitetura em camadas baseada no ecossistema Spring:

- Controller: exposição dos endpoints REST  
- Service: regras de negócio  
- Repository: acesso ao banco de dados (JPA/Hibernate)  
- DTOs: comunicação entre camadas  
- Security: autenticação e autorização com JWT  

---

## ⚙️ Tecnologias Utilizadas

Backend:
- Java 17  
- Spring Boot 3  
- Spring Data JPA  
- Hibernate  
- Spring Security (JWT)  

Banco de Dados:
- PostgreSQL (produção)  
- H2 (testes)  

Infraestrutura:
- Docker  
- Railway (deploy anterior)  
- Maven  

Ferramentas:
- Postman  
- Swagger  

---

## 🔐 Autenticação e Segurança

O sistema implementa autenticação via JWT (JSON Web Token) com controle de acesso por perfis:

- Dona da loja: acesso ao dashboard financeiro  
- Funcionário: gestão de pedidos  

✔ Senhas criptografadas  
✔ Controle de permissões (RBAC)  
✔ Uso de variáveis de ambiente  

---

## 📡 Principais Endpoints

### Autenticação
POST /auth/login  
POST /auth/register  

### Usuários
GET /users  
POST /users  

### Pedidos
GET /orders  
POST /orders  
PUT /orders/{id}  
DELETE /orders/{id}  

### Dashboard
GET /dashboard  

---

## 📊 Funcionalidades

Gestão de Usuários:
- Cadastro e autenticação  
- Controle de acesso por perfil  

Gestão de Pedidos:
- Criação e atualização de pedidos  
- Controle de status (pendente, pago, entregue)  

Controle Financeiro:
- Registro de pagamentos  
- Visualização de faturamento  

Geração de Comprovantes:
- Emissão automática de PDF após pagamento  
- Documento formatado com dados do pedido  

---

## 🖼️ Telas do Sistema

### Login
![Login](docs/login.png.png)

### Dashboard da Dona
![Dashboard](docs/dashboard-dona.png.png)

### Funcionário
![Funcionário](docs/funcionario.png.png)

### Pedido
![Pedido](docs/pedido.png.png)

### Comprovante PDF
![Comprovante](docs/comprovante-pdf.png.png)

---

## 🚀 Como rodar localmente

1. Clonar o projeto:
git clone <url-do-repositorio>
cd keley-bolos-api

2. Configurar variáveis de ambiente:
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/keley  
SPRING_DATASOURCE_USERNAME=postgres  
SPRING_DATASOURCE_PASSWORD=senha  
JWT_SECRET=sua_chave  

3. Rodar aplicação:
mvn spring-boot:run  

A API estará disponível em:
http://localhost:8080  

---

## 📚 Aprendizados

- Desenvolvimento de APIs REST com Spring Boot  
- Arquitetura em camadas  
- Autenticação e segurança com JWT  
- Integração com banco de dados relacional  
- Deploy em ambiente cloud  
- Boas práticas de backend  

---

## 🎯 Objetivo

Simular um ambiente real de desenvolvimento backend, aplicando conceitos profissionais de arquitetura, segurança e estruturação de APIs.

---

## 👩‍💻 Autora

Izabela Xavier  
Backend Developer | Java | Spring Boot  

GitHub: https://github.com/izabelaxavier  
LinkedIn: https://www.linkedin.com/in/izabela-xavier-dev/
