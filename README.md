# RPG Character Creator API

API REST desenvolvida em Java utilizando Spring Boot para gerenciamento de campanhas de RPG.

O projeto possui autenticação, gerenciamento de usuários, personagens, campanhas e sistema de convites entre jogadores, aplicando regras de negócio e controle de permissões.

---

# Tecnologias

- Java 25
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- BCrypt
- Swagger

---

# Funcionalidades

## Usuários

- Cadastro de usuários
- Senhas criptografadas
- Autenticação Basic Auth

## Personagens

- Criar personagem
- Editar personagem
- Excluir personagem
- Buscar personagem
- Pesquisa por nome
- Pesquisa por raça
- Pesquisa por classe

## Campanhas

- Criar campanha
- Excluir campanha
- Visualizar detalhes
- Adicionar personagem
- Remover personagem
- Jogador sair da campanha

## Convites

- Enviar convite
- Aceitar convite
- Listar convites
- Impedir convites duplicados

## Segurança

- Controle de permissões
- Apenas o dono pode excluir sua ficha
- Mestres podem visualizar e editar apenas personagens pertencentes às suas campanhas
- Usuários sem permissão recebem HTTP 403

---

# Arquitetura

```
Controller
      ↓
Service
      ↓
Repository
      ↓
PostgreSQL
```

---

# Estrutura do projeto

```
controller
dto
exception
model
repository
security
service
```

---

# Como executar

Clone o projeto

```bash
git clone https://github.com/LucasBS-Dev/character-creator.git
```

Entre na pasta

```bash
cd character-creator
```

Configure o PostgreSQL.

Execute

```bash
mvn spring-boot:run
```

Ou execute diretamente a classe

```
CharacterCreatorApplication
```

---

# Futuras melhorias

Este projeto foi desenvolvido com foco exclusivamente no backend.

A API está preparada para integração com qualquer frontend (React, Angular, Vue, Flutter etc.).

---

# Autor

Lucas Silva

Estudante de Ciência da Computação

Java Backend Developer