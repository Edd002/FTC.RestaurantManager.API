# FTC.UserManager.API
 Projeto Tech Challenge do Curso de Pós-Graduação Lato Sensu Arquitetura e Desenvolvimento em JAVA da Faculdade de Informática e Administração Paulista (FIAP)

### Como rodar? 🚀
Para executar o projeto utilizando Docker Compose:
1. Crie um arquivo **.env** na raiz do projeto com suas configurações (PS.: Utilize como base o arquivo [.env.example](.env.example))
2. Rode `docker-compose up` na raiz do projeto
3. Acesse o swagger: [link](http://localhost:8084/user-manager/swagger-ui/index.html)
4. Acesse o pgAdmin utilizando as credencias do .env: [link](http://localhost:80)

<details>
<summary>Criar usuário - Exemplo</summary>

- Encontre um hashID válido `SELECT * FROM t_city;`

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
</details>
