# FTC.RestaurantManager.API
 Projeto Tech Challenge do Curso de Pós-Graduação Lato Sensu Arquitetura e Desenvolvimento em JAVA da Faculdade de Informática e Administração Paulista (FIAP)

## Arquitetura do Sistema

Este sistema foi desenvolvido utilizando **Java 21**, **Spring Boot 3.4.4** e **PostgreSQL 17.4**.

### Tecnologias Utilizadas
- **Java 21** e **Spring Boot 3.4.4** para a criação da aplicação web
- **Docker** e **Docker Compose** para execução e gerenciamento de ambientes
- **PostgreSQL** como banco de dados, garantindo confiabilidade e desempenho
- **Flyway** para gerenciamento de migrações do banco de dados
- **H2 Database Engine** como banco de dados para ambiente de testes automatizados
- **Swagger OpenAPI Specification** como documentação interativa da API

### Estrutura Arquitetural
A arquitetura do sistema segue uma abordagem baseada em **Domínios** e **Clean Architecture**, promovendo a separação de responsabilidades e facilitando a escalabilidade. Os principais componentes dentro de cada domínio incluem:

- **Config**: Configuração de dependências necessárias para o levantamento e funcionamento do container de aplicação.
- **Controller**: Gerencia requisições HTTP e as direciona para os serviços apropriados.
- **Service**: Coordena acessos sistêmicos como arquivos de configuração e repositórios.
- **Repository**: Interface para acesso e manipulação de dados armazenados no banco de dados.
- **Entity**: Representação das tabelas do banco de dados como classes Java (ORM) e core de domínio.
- **UseCases**: Implementações de regras de negócio para cada caso de uso de cada domínio.

### Benefícios da Arquitetura
Essa estrutura modular possibilita:
- Desenvolvimento mais organizado
- Manutenção facilitada
- Maior flexibilidade para futuras expansões

## Como rodar? 🚀
Para executar o projeto utilizando Docker Compose:
1. Crie um arquivo **.env** na raiz do projeto com suas configurações (PS.: Utilize como base o arquivo [.env.example](.env.example))
2. Rode `docker compose --profile docker up` na raiz do projeto
3. Acesse o Swagger: [link](http://localhost:8085/restaurant-manager/swagger-ui/index.html)
4. Acesse o pgAdmin utilizando as credencias do .env: [link](http://localhost:80)
5. Acesse a documentação Postman: [link](https://documenter.getpostman.com/view/43787842/2sB2qcBfps)

### Como acessar o banco de dados em memória H2 de testes automatizados via console?
Em contexto de desenvolvimento, enquanto os testes automatizados estiverem em execução ou em pausa (thread breakpoint) é possível acessar a estrutura do banco de dados enquanto está em memória em http://localhost:8085/restaurant-manager/h2-console com as credenciais:

Driver Class: org.h2.Driver<br>
JDBC URL: jdbc:h2:tcp://localhost:9092/mem:db<br>
User Name: sa<br>
Password:<br>

O breakpoint pode ser configurado para suspender apenas uma única thread para que o acesso ao H2-console seja possível (https://hrrbrt.medium.com/using-h2-during-test-debugging-in-spring-f6a3db355e3a).
