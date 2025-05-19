# FTC.RestaurantManager.API
 Projeto Tech Challenge do Curso de Pós-Graduação Lato Sensu Arquitetura e Desenvolvimento em JAVA da Faculdade de Informática e Administração Paulista (FIAP)

## Arquitetura do Sistema

Este sistema foi desenvolvido utilizando **Java 21** e **Spring Boot**, proporcionando uma base robusta para a construção de serviços web.

### Tecnologias Utilizadas
- **Java 21** e **Spring Boot** para a criação da aplicação
- **Docker** e **Docker Compose** para execução e gerenciamento de ambientes
- **PostgreSQL** como banco de dados, garantindo confiabilidade e desempenho
- **Flyway** para gerenciamento de migrações do banco de dados

### Estrutura Arquitetural
A arquitetura do sistema segue uma abordagem baseada em **domínios**, promovendo a separação de responsabilidades e facilitando a escalabilidade. Os principais componentes dentro de cada domínio incluem:

- **Controller**: Gerencia requisições HTTP e as direciona para os serviços apropriados.
- **Service**: Contém a lógica de negócios e coordena interações entre componentes.
- **Repository**: Interface para acesso e manipulação de dados armazenados no banco de dados.
- **Entity**: Representação das tabelas do banco de dados como classes Java.
- **Use Cases**: Implementações específicas para cada caso de uso dentro do domínio, como operações relacionadas a usuários.

### Benefícios da Arquitetura
Essa estrutura modular possibilita:
- Desenvolvimento mais organizado
- Manutenção facilitada
- Maior flexibilidade para futuras expansões

## Como rodar? 🚀
Para executar o projeto utilizando Docker Compose:
1. Crie um arquivo **.env** na raiz do projeto com suas configurações (PS.: Utilize como base o arquivo [.env.example](.env.example))
2. Rode `docker compose --profile docker up` na raiz do projeto
3. Acesse o swagger: [link](http://localhost:8085/restaurant-manager/swagger-ui/index.html)
4. Acesse o pgAdmin utilizando as credencias do .env: [link](http://localhost:80)

<details>
<summary>Criar usuário - Exemplo</summary>

- Encontre um hash id de cidade válido em http://localhost:8085/restaurant-manager/swagger-ui/index.html#/Cidades%20-%20Endpoints%20de%20Cidades/find_3 com a aplicação em execução.

```json
{
  "address": {
    "cep": "35090-650",
    "complement": "Complemento 123",
    "description": "Rua 123",
    "hashIdCity": "258eece0f1df410ea8c706085397d812",
    "neighborhood": "Alta Floresta D´oeste",
    "number": "100",
    "postalCode": "1234-5678"
  },
  "email": "manu@example.com",
  "login": "manu_002",
  "name": "manu",
  "password": "manu2025"
}
```

### Como acessar o banco de dados em memória H2 de testes automatizados via console?
Enquanto os testes estiverem em execução ou em pausa (thread breakpoint) é possível acessar a estrutura do banco de dados enquanto está em memória em http://localhost:8085/restaurant-manager/h2-console com as credenciais:

Driver Class: org.h2.Driver<br>
JDBC URL: jdbc:h2:tcp://localhost:9092/mem:db<br>
User Name: sa<br>
Password:<br>

O breakpoint pode ser configurado para suspender apenas uma única thread para que o acesso ao H2-console seja possível (https://hrrbrt.medium.com/using-h2-during-test-debugging-in-spring-f6a3db355e3a).
</details>