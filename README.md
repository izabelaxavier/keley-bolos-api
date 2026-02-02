# 🍰 Keley Bolos API

API backend desenvolvida como projeto de estudo e portfólio, simulando um sistema real para gerenciamento de cardápio e dados da loja **Keley Bolos**.

O objetivo do projeto é praticar **Java + Spring Boot**, integração com banco de dados, deploy em produção e boas práticas de backend.

---

## 🚀 Tecnologias utilizadas

* Java 17
* Spring Boot 3
* Spring Data JPA
* PostgreSQL
* Hibernate
* Maven
* Render (Deploy)

---

## 🌐 Aplicação em produção

A API está publicada e rodando em produção:

👉 [https://sistema-keley-bolos.onrender.com](https://sistema-keley-bolos.onrender.com)

---

## 🗄️ Banco de dados

* PostgreSQL em ambiente cloud
* Configuração via **variáveis de ambiente** (sem credenciais no código)
* Criação automática das tabelas com `hibernate.ddl-auto=update`

---

## 🔐 Segurança

Nenhuma senha ou credencial sensível é versionada no repositório.

As configurações utilizam variáveis de ambiente:

* `SPRING_DATASOURCE_URL`
* `DATABASE_USERNAME`
* `DATABASE_PASSWORD`

---

## ▶️ Como rodar o projeto localmente

1. Clone o repositório
2. Configure as variáveis de ambiente do banco PostgreSQL
3. Execute o projeto:

```bash
mvn spring-boot:run
```

A aplicação irá subir por padrão na porta `8080`.

---

## 📚 Aprendizados

* Estruturação de uma API REST com Spring Boot
* Integração com banco PostgreSQL
* Uso correto de variáveis de ambiente
* Deploy de backend em produção
* Debug e resolução de erros reais de infraestrutura

---

## 👩‍💻 Autora

**Izabela Xavier**
Estudante de Análise e Desenvolvimento de Sistemas
Backend | Java | Spring Boot
