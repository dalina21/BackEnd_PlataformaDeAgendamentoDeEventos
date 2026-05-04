# Plataforma de Agendamento de Eventos
### Sobre o Projeto

Este projeto consiste no desenvolvimento de uma API RESTful para gerenciamento de eventos, visando atender regras de negócio envolvendo dois perfis:

- Organizadores: responsáveis pelo gerenciamento de eventos e postagens
- Participantes: responsáveis pela inscrição e interação com eventos

### Arquitetura

A aplicação segue o padrão MVC, com separação clara de responsabilidades:

- Model: entidades e regras de negócio
- Controller: exposição dos endpoints REST
- Repository: acesso aos dados com Spring Data JPA
- Service: camada de lógica de negócio

A comunicação é feita via HTTP utilizando padrões REST.

### Padrões de Projeto
- Decorator: utilizado para adicionar comportamentos dinamicamente
- Facade: utilizado para simplificar interações entre componentes internos

### Tecnologias
- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Lombok
- Swagger / OpenAPI
- Maven
- PostgreSQL

### Funcionalidades da API
#### Usuários
- Cadastro de participantes
- Autenticação
- Atualização de dados
- Criptografia de senha com BCrypt

#### Eventos
- Criação de eventos
- Edição e exclusão
- Listagem de eventos
- Ordenação por número de participantes
- Filtros por status (disponível / encerrado)

#### Inscrições
- Inscrição em eventos
- Cancelamento de inscrição
- Listagem de eventos do participante

#### Postagens
- Criação de postagens em eventos
- Exclusão de postagens
- Listagem de atualizações por evento

#### Notificações
- Notificação de participantes sobre novas postagens

### Regras de Negócio
- Emails não podem ser duplicados
- Senhas devem possuir no mínimo 8 caracteres
- Senhas são armazenadas criptografadas
- Integridade dos dados garantida via validações

## Persistência de Dados
- Utilização de JPA com Hibernate
- Mapeamento objeto-relacional (ORM)
- Integração com PostgreSQL
- Controle de consistência e validações
