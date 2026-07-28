# API REST de Produtos com Spring Boot

Projeto desenvolvido como parte de um desafio prático sobre **Padrões de Projeto com Java e Spring**.

A proposta do desafio era consolidar os conhecimentos adquiridos durante o curso, permitindo reproduzir e evoluir um projeto de referência ou desenvolver uma nova API aplicando os padrões estudados.

Para diferenciar do projeto apresentado nas aulas, foi criada do zero uma **API REST para gerenciamento de produtos**, utilizando Java, Spring Boot, Spring Data JPA e banco de dados H2.

---

## Objetivo do projeto

Desenvolver uma API REST capaz de realizar o gerenciamento completo de produtos, aplicando boas práticas de organização em camadas e conceitos relacionados a padrões de projeto.

A aplicação permite:

* cadastrar produtos;
* listar todos os produtos;
* buscar um produto pelo ID;
* atualizar um produto;
* ativar ou desativar um produto;
* excluir um produto;
* validar os dados recebidos;
* retornar erros de forma organizada.

---

## Tecnologias utilizadas

* Java 21
* Spring Boot
* Spring Web
* Spring Data JPA
* Hibernate
* Jakarta Bean Validation
* Lombok
* Maven
* H2 Database
* IntelliJ IDEA
* Postman

---

## Padrões e conceitos aplicados

### Singleton

Por padrão, os componentes gerenciados pelo Spring são criados como uma única instância dentro do contexto da aplicação.

Classes como `ProdutoServiceImpl` são gerenciadas pelo Spring por meio da anotação `@Service`.

```java
@Service
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {
}
```

---

### Strategy

O padrão Strategy é representado pela separação entre a interface de serviço e sua implementação.

```java
public interface ProdutoService {
}
```

```java
@Service
public class ProdutoServiceImpl implements ProdutoService {
}
```

Dessa forma, o Controller depende do contrato definido pela interface, e não diretamente de uma implementação específica.

---

### Repository

O padrão Repository abstrai a comunicação com o banco de dados.

```java
public interface ProdutoRepository
        extends JpaRepository<Produto, Long> {
}
```

O Spring Data JPA fornece automaticamente operações como:

* `save`;
* `findAll`;
* `findById`;
* `existsById`;
* `deleteById`.

Assim, não é necessário escrever manualmente os comandos SQL das operações básicas.

---

### Facade

A camada de serviço atua como uma fachada para as operações relacionadas aos produtos.

O Controller não precisa conhecer os detalhes de acesso ao banco de dados. Ele apenas solicita as operações ao Service.

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Banco de dados
```

---

## Arquitetura do projeto

O projeto foi organizado em camadas:

```text
src/main/java/br/com/fernando/produtosapi
│
├── controller
│   └── ProdutoController.java
│
├── dto
│   └── ProdutoStatusRequest.java
│
├── exception
│   └── GlobalExceptionHandler.java
│
├── model
│   └── Produto.java
│
├── repository
│   └── ProdutoRepository.java
│
├── service
│   ├── ProdutoService.java
│   └── ProdutoServiceImpl.java
│
└── ProdutosApiApplication.java
```

### Responsabilidade das camadas

* **Controller:** recebe as requisições HTTP e retorna as respostas.
* **Service:** concentra as operações e regras de negócio.
* **Repository:** realiza a comunicação com o banco de dados.
* **Model:** representa a entidade do sistema.
* **DTO:** representa dados específicos recebidos pela API.
* **Exception:** centraliza o tratamento de erros.

---

## Entidade Produto

Cada produto possui os seguintes atributos:

| Campo               | Tipo       | Descrição                            |
| ------------------- | ---------- | ------------------------------------ |
| `id`                | Long       | Identificador gerado automaticamente |
| `nome`              | String     | Nome do produto                      |
| `descricao`         | String     | Descrição do produto                 |
| `preco`             | BigDecimal | Preço do produto                     |
| `quantidadeEstoque` | Integer    | Quantidade disponível em estoque     |
| `categoria`         | String     | Categoria do produto                 |
| `ativo`             | Boolean    | Indica se o produto está ativo       |

---

## Endpoints

A URL base da aplicação é:

```text
http://localhost:8081
```

### Cadastrar produto

```http
POST /produtos
```

Exemplo de requisição:

```json
{
  "nome": "Hambúrguer Artesanal",
  "descricao": "Pão, carne artesanal, queijo e bacon",
  "preco": 32.90,
  "quantidadeEstoque": 20,
  "categoria": "Lanches",
  "ativo": true
}
```

Resposta esperada:

```http
201 Created
```

---

### Listar produtos

```http
GET /produtos
```

Resposta esperada:

```http
200 OK
```

---

### Buscar produto pelo ID

```http
GET /produtos/{id}
```

Exemplo:

```http
GET /produtos/1
```

Respostas possíveis:

```http
200 OK
```

```http
404 Not Found
```

---

### Atualizar produto

```http
PUT /produtos/{id}
```

Exemplo:

```http
PUT /produtos/1
```

Corpo da requisição:

```json
{
  "nome": "Hambúrguer Artesanal Duplo",
  "descricao": "Pão, duas carnes, queijo e bacon",
  "preco": 39.90,
  "quantidadeEstoque": 15,
  "categoria": "Lanches",
  "ativo": true
}
```

Respostas possíveis:

```http
200 OK
```

```http
404 Not Found
```

---

### Ativar ou desativar produto

```http
PATCH /produtos/{id}/status
```

Exemplo:

```http
PATCH /produtos/1/status
```

Corpo da requisição:

```json
{
  "ativo": false
}
```

Respostas possíveis:

```http
200 OK
```

```http
404 Not Found
```

---

### Excluir produto

```http
DELETE /produtos/{id}
```

Exemplo:

```http
DELETE /produtos/1
```

Respostas possíveis:

```http
204 No Content
```

```http
404 Not Found
```

---

## Validações

A API possui validações para impedir o cadastro de dados inválidos.

Entre as validações implementadas estão:

* nome obrigatório;
* limite de caracteres para o nome;
* limite de caracteres para a descrição;
* preço obrigatório;
* preço mínimo de R$ 0,01;
* quantidade em estoque obrigatória;
* estoque não pode ser negativo;
* categoria obrigatória;
* status obrigatório.

Exemplo de resposta de validação:

```json
{
  "status": 400,
  "erro": "Dados inválidos",
  "campos": {
    "nome": "O nome do produto é obrigatório",
    "preco": "O preço deve ser maior ou igual a R$ 0,01",
    "quantidadeEstoque": "A quantidade em estoque não pode ser negativa"
  }
}
```

---

## Banco de dados H2

O projeto utiliza o banco de dados H2 em memória.

Isso significa que os dados são apagados quando a aplicação é encerrada.

O console do H2 pode ser acessado em:

```text
http://localhost:8081/h2-console
```

Utilize as seguintes informações:

```text
JDBC URL: jdbc:h2:mem:produtosdb
User Name: sa
Password:
```

A senha deve permanecer vazia.

Para consultar os produtos diretamente no banco:

```sql
SELECT * FROM PRODUTOS;
```

---

## Como executar o projeto

### Pré-requisitos

Antes de executar, é necessário ter instalado:

* Java 21;
* Git;
* IntelliJ IDEA ou outra IDE compatível;
* Maven, caso não utilize o Maven Wrapper do projeto.

### Clonar o repositório

```bash
git clone https://github.com/nizuG/api_java_spring_crud_produtos.git
```

Entre na pasta:

```bash
cd produtos-api
```

### Executar pelo IntelliJ

1. Abra o projeto no IntelliJ IDEA.
2. Aguarde o Maven baixar as dependências.
3. Abra a classe `ProdutosApiApplication`.
4. Execute o método `main`.
5. A API estará disponível na porta `8081`.

### Executar pelo terminal

No Windows:

```bash
mvnw.cmd spring-boot:run
```

No Linux ou macOS:

```bash
./mvnw spring-boot:run
```

---

## Testando a API

Os endpoints podem ser testados utilizando:

* Postman;
* Insomnia;
* Bruno;
* cURL;
* qualquer cliente HTTP.

Exemplo utilizando cURL:

```bash
curl -X GET http://localhost:8081/produtos
```

---

## Sobre o desafio

Este projeto foi desenvolvido para a etapa final de um desafio sobre **Padrões de Projeto — Design Patterns**.

O desafio permitia diferentes abordagens:

* reproduzir e evoluir o projeto criado durante as aulas;
* desenvolver uma nova API aplicando os padrões estudados;
* implementar um padrão de projeto específico;
* utilizar Java puro ou Spring Framework.

Neste projeto, foi escolhida a abordagem de **criar uma API do zero**, aplicando os conceitos apresentados durante o curso em um domínio diferente do projeto de referência.

---

## Melhorias futuras

Como possíveis evoluções do projeto:

* documentação com Swagger/OpenAPI;
* utilização de DTOs para cadastro e resposta;
* paginação de produtos;
* filtros por nome, categoria e status;
* integração com PostgreSQL;
* migrations com Flyway;
* testes unitários;
* testes de integração;
* autenticação com Spring Security;
* controle de usuários e permissões;
* publicação da aplicação em ambiente de nuvem.

---

## Autor

Desenvolvido por **Gustavo Macedo* como projeto de estudo e prática de Java, Spring Boot e padrões de projeto.
