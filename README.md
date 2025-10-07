# Backend - Projeto E-commerce PCForYou 🖥️

Este repositório contém o código-fonte do **backend** para o projeto **PCForYou**, um e-commerce de peças e computadores desenvolvido para a disciplina de desenvolvimento web na UniBH.

Esta API RESTful é responsável por toda a lógica de negócio, gerenciamento de produtos, controle de estoque e processamento de vendas. Ela foi projetada para ser consumida por uma aplicação frontend separada.

---

## 🔗 Frontend

Assim que o frontend estiver disponível, o link será adicionado aqui.

---

## 🚀 Tecnologias Utilizadas

* **Java 17+**
* **Spring Boot 3+**
* **Spring Web**
* **Spring Data JPA**
* **Bean Validation**
* **Maven**
* **Banco de Dados H2** (Atualmente em memória, mas futuramente será migrado para PostgreSQL)
* **Lombok** (para reduzir boilerplate code)

---

## ⚙️ Como Executar o Projeto

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/Edu136/api-ecommerce-pcforyou
    ```

2.  **Navegue até o diretório do projeto:**
    ```bash
    cd api-ecommerce-pcforyou
    ```

3.  **Execute a aplicação com o Maven:**
    ```bash
    mvn spring-boot:run
    ```

4.  A API estará disponível em `http://localhost:8080`.

---

## 📖 Endpoints da API

A URL base para todos os endpoints é `/produtos`.

### 1. Listar todos os produtos

Retorna uma lista com todos os produtos disponíveis no e-commerce.

* **Método:** `GET`
* **URL:** `/produtos`
* **Resposta de Sucesso (200 OK):**
    ```json
    [
        {
            "id": 1,
            "nome": "Placa de Vídeo RTX 4080",
            "preco": 7500.00,
            "quantidade": 15
        },
        {
            "id": 2,
            "nome": "Processador Intel Core i9-13900K",
            "preco": 3899.99,
            "quantidade": 25
        }
    ]
    ```

<br/>

### 2. Adicionar um novo produto

Cadastra um novo produto no catálogo da loja.

* **Método:** `POST`
* **URL:** `/produtos/add`
* **Corpo da Requisição (Request Body):**
    ```json
    {
        "nome": "Memória RAM DDR5 16GB 5200MHz",
        "preco": 450.00,
        "quantidade": 80
    }
    ```
* **Resposta de Sucesso (201 Created):**
    ```json
    {
        "id": 3,
        "nome": "Memória RAM DDR5 16GB 5200MHz",
        "preco": 450.00,
        "quantidade": 80
    }
    ```
* **Resposta de Erro (400 Bad Request):** Retornada se algum campo obrigatório não for enviado ou se os dados forem inválidos (ex: preço negativo).

<br/>

### 3. Vender um produto

Registra a venda de uma determinada quantidade de um produto, diminuindo o valor do estoque.

* **Método:** `POST`
* **URL:** `/produtos/vender/{id}`
    * Substitua `{id}` pelo ID do produto. Exemplo: `/produtos/vender/1`
* **Corpo da Requisição (Request Body):**
    ```json
    {
        "quantidade": 1
    }
    ```
* **Resposta de Sucesso (200 OK):** Nenhum corpo de resposta (vazio). O status `200 OK` indica que a operação foi bem-sucedida.
* **Respostas de Erro:**
    * **400 Bad Request:** Se a quantidade a ser vendida for maior que o estoque disponível.
    * **404 Not Found:** Se o produto com o `id` informado não for encontrado.

---

## 📝 Estrutura dos DTOs

Os Data Transfer Objects (DTOs) são usados para transferir dados entre o cliente (frontend) e o servidor (esta API).

### `CreateProdutoRequestDTO`
Usado para criar um novo produto.
```java
// br.unibh.produtos.dto.CreateProdutoRequestDTO
public class CreateProdutoRequestDTO {
    private String nome;
    private Double preco;
    private Integer quantidade;
}
```

### `ReponseCreateProdutoDTO`
Retornado após a criação de um produto.
```java
// br.unibh.produtos.dto.ReponseCreateProdutoDTO
public class ReponseCreateProdutoDTO {
    private Long id;
    private String nome;
    private Double preco;
    private Integer quantidade;
}
```

### `VenderProdutoRequestDTO`
Usado para especificar a quantidade a ser vendida.
```java
// br.unibh.produtos.dto.VenderProdutoRequestDTO
public class VenderProdutoRequestDTO {
    private Integer quantidade;
}
```
