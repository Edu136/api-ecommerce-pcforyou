# Backend - Projeto E-commerce PCForYou 🖥️

Este repositório contém o código-fonte do **backend** para o projeto **PCForYou**, um e-commerce de peças e computadores desenvolvido para a disciplina de desenvolvimento web na UniBH.

<<<<<<< HEAD
Esta API RESTful é responsável por toda a lógica de negócio, gerenciamento de produtos, usuários, endereços, controle de estoque e processamento de vendas. Ela foi projetada para ser consumida por uma aplicação frontend separada.
=======
Esta API RESTful é responsável por toda a lógica de negócio, gerenciamento de produtos, controle de estoque e processamento de vendas. Ela foi projetada para ser consumida por uma aplicação frontend separada.
>>>>>>> dbd70094e754c2fe4e568c7da2fa2ca4dd31df56

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
<<<<<<< HEAD
    git clone [https://github.com/Edu136/api-ecommerce-pcforyou](https://github.com/Edu136/api-ecommerce-pcforyou)
=======
    git clone https://github.com/Edu136/api-ecommerce-pcforyou
>>>>>>> dbd70094e754c2fe4e568c7da2fa2ca4dd31df56
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

<<<<<<< HEAD
A seguir, a documentação detalhada dos endpoints disponíveis.

### Produtos (`/produtos`)

Endpoints para gerenciar o catálogo de produtos.

#### 1. Listar todos os produtos
Retorna uma lista com todos os produtos disponíveis.
=======
A URL base para todos os endpoints é `/produtos`.

### 1. Listar todos os produtos

Retorna uma lista com todos os produtos disponíveis no e-commerce.

>>>>>>> dbd70094e754c2fe4e568c7da2fa2ca4dd31df56
* **Método:** `GET`
* **URL:** `/produtos`
* **Resposta de Sucesso (200 OK):**
    ```json
    [
        {
            "id": 1,
            "nome": "Placa de Vídeo RTX 4080",
<<<<<<< HEAD
            "descricao": "Placa de vídeo de alta performance para jogos em 4K e aplicações gráficas intensivas.",
            "preco": 7500.00,
            "quantidade": 15,
            "status": "DISPONIVEL",
            "idImages" : [1, 2]
        }
    ]
    ```

#### 2. Listar produtos por status
Retorna uma lista de produtos filtrando pelo seu status (ex: "DISPONIVEL", "INDISPONIVEL").
* **Método:** `GET`
* **URL:** `/produtos/{status}`
    * Substitua `{status}` pelo status desejado. Exemplo: `/produtos/DISPONIVEL`
* **Resposta de Sucesso (200 OK):**
    ```json
    [
=======
            "preco": 7500.00,
            "quantidade": 15
        },
>>>>>>> dbd70094e754c2fe4e568c7da2fa2ca4dd31df56
        {
            "id": 2,
            "nome": "Processador Intel Core i9-13900K",
            "preco": 3899.99,
<<<<<<< HEAD
            "quantidade": 25,
            "status": "DISPONIVEL",
            "idImages" : [1,2]
=======
            "quantidade": 25
>>>>>>> dbd70094e754c2fe4e568c7da2fa2ca4dd31df56
        }
    ]
    ```

<<<<<<< HEAD
#### 3. Adicionar um novo produto
Cadastra um novo produto no catálogo da loja.
=======
<br/>

### 2. Adicionar um novo produto

Cadastra um novo produto no catálogo da loja.

>>>>>>> dbd70094e754c2fe4e568c7da2fa2ca4dd31df56
* **Método:** `POST`
* **URL:** `/produtos/add`
* **Corpo da Requisição (Request Body):**
    ```json
    {
        "nome": "Memória RAM DDR5 16GB 5200MHz",
<<<<<<< HEAD
        "descricao": "Memória RAM DDR5 de alta performance para jogos e aplicações pesadas.",
=======
>>>>>>> dbd70094e754c2fe4e568c7da2fa2ca4dd31df56
        "preco": 450.00,
        "quantidade": 80
    }
    ```
* **Resposta de Sucesso (201 Created):**
    ```json
    {
        "id": 3,
        "nome": "Memória RAM DDR5 16GB 5200MHz",
<<<<<<< HEAD
        "descricao": "Memória RAM DDR5 de alta performance para jogos e aplicações pesadas.",
        "preco": 450.00,
        "quantidade": 80,
        "status": "DISPONIVEL"
    }
    ```

#### 4. Editar um produto existente
Atualiza as informações de um produto específico.
* **Método:** `PUT`
* **URL:** `/produtos/editar/{id}`
    * Substitua `{id}` pelo ID do produto.
* **Corpo da Requisição (Request Body):**
    ```json
    {
        "nome": "Placa de Vídeo RTX 4080 Super",
        "descricao": "Placa de vídeo de alta performance para jogos em 4K e aplicações gráficas intensivas.",
        "preco": 7999.00,
        "quantidade": 10
    }
    ```
* **Resposta de Sucesso (200 OK):**
    ```json
    {
        "id": 1,
        "nome": "Placa de Vídeo RTX 4080 Super",
        "descricao": "Placa de vídeo de alta performance para jogos em 4K e aplicações gráficas intensivas.",
        "preco": 7999.00,
        "quantidade": 10,
        "status": "DISPONIVEL",
        "idImages" : [1, 2]
    }
    ```
* **Resposta de Erro (404 Not Found):** Se o produto com o `id` informado não for encontrado.

#### 5. Vender um produto
Registra a venda de uma quantidade de um produto, diminuindo o estoque.
* **Método:** `POST`
* **URL:** `/produtos/vender/{id}`
    * Substitua `{id}` pelo ID do produto.
=======
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
>>>>>>> dbd70094e754c2fe4e568c7da2fa2ca4dd31df56
* **Corpo da Requisição (Request Body):**
    ```json
    {
        "quantidade": 1
    }
    ```
<<<<<<< HEAD
* **Resposta de Sucesso (200 OK):** Nenhum corpo de resposta.
* **Respostas de Erro:**
    * **400 Bad Request:** Se a quantidade a ser vendida for maior que o estoque.
    * **404 Not Found:** Se o produto com o `id` não for encontrado.

#### 6. Excluir um produto
Remove um produto permanentemente do catálogo com base no seu ID.
* **Método:** `DELETE`
* **URL:** `/produtos/delete/{id}`
    * Substitua `{id}` pelo ID do produto a ser excluído.
* **Resposta de Sucesso (204 No Content):** Nenhum corpo de resposta. O status `204` indica que a operação foi concluída com sucesso.
* **Resposta de Erro (404 Not Found):** Retornada se o produto com o `id` informado não for encontrado.

<br/>

### Usuários (`/users`)
Endpoints para gerenciamento de usuários e autenticação. A URL base é `/users`.

#### 1. Criar novo usuário
Registra um novo usuário no sistema.
* **Método:** `POST`
* **URL:** `/users`
* **Corpo da Requisição (Request Body):**
    ```json
    {
        "nome": "João Silva",
        "email": "joao.silva@email.com",
        "senha": "senhaSegura123"
    }
    ```
* **Resposta de Sucesso (200 OK):** Nenhum corpo de resposta.

#### 2. Listar todos os usuários
Retorna uma lista com todos os usuários cadastrados.
* **Método:** `GET`
* **URL:** `/users`
* **Resposta de Sucesso (200 OK):**
    ```json
    [
        {
            "id": 1,
            "nome": "João Silva",
            "email": "joao.silva@email.com"
        }
    ]
    ```

#### 3. Realizar login
Autentica um usuário com base em seu e-mail e senha.
* **Método:** `POST`
* **URL:** `/users/login`
* **Corpo da Requisição (Request Body):**
    ```json
    {
        "email": "joao.silva@email.com",
        "senha": "senhaSegura123"
    }
    ```
* **Resposta de Sucesso (200 OK):** Nenhum corpo de resposta.
* **Resposta de Erro (401 Unauthorized):** Se as credenciais forem inválidas.

#### 4. Alterar senha do usuário
Atualiza a senha de um usuário específico, identificado pelo seu ID.

* **Método:** `PATCH`
* **URL:** `/users/editPassword/{id}`
    * Substitua `{id}` pelo ID do usuário.
* **Corpo da Requisição (Request Body):** Um objeto JSON contendo a nova senha.
    ```json
    {
        "novaSenha": "novaSenhaSegura456"
    }
    ```
* **Resposta de Sucesso (200 OK):** Nenhum corpo de resposta. O status indica que a senha foi alterada com sucesso.
* **Resposta de Erro (404 Not Found):** Retornada se o usuário com o `id` informado não for encontrado.
<br/>

### Endereços (`/enderecos`)
Endpoints para o gerenciamento de endereços dos usuários. A URL base é `/enderecos`.

#### 1. Adicionar novo endereço
Cadastra um novo endereço para um usuário.
* **Método:** `POST`
* **URL:** `/enderecos/add`
* **Corpo da Requisição (Request Body):**
    ```json
    {
        "cep": "30110-001",
        "logradouro": "Avenida do Contorno",
        "numero": "2000",
        "complemento": "Apto 101",
        "bairro": "Funcionários",
        "cidade": "Belo Horizonte",
        "estado": "MG",
        "userId": 1
    }
    ```
* **Resposta de Sucesso (200 OK):**
    ```json
    {
        "id": 1,
        "cep": "30110-001",
        "logradouro": "Avenida do Contorno",
        "numero": "2000",
        "complemento": "Apto 101",
        "bairro": "Funcionários",
        "cidade": "Belo Horizonte",
        "estado": "MG"
    }
    ```

#### 2. Editar um endereço
Atualiza as informações de um endereço existente.
* **Método:** `PUT`
* **URL:** `/enderecos/editar/{id}`
    * Substitua `{id}` pelo ID do endereço.
* **Corpo da Requisição (Request Body):** (Mesma estrutura da criação)
* **Resposta de Sucesso (200 OK):** (Mesma estrutura da resposta de criação)
* **Resposta de Erro (404 Not Found):** Se o endereço com o `id` informado não for encontrado.


#### 3. Excluir um endereço
Remove um endereço do banco de dados com base no seu ID.
* **Método:** `DELETE`
* **URL:** `/enderecos/delete/{id}`
    * Substitua `{id}` pelo ID do endereço a ser excluído.
* **Resposta de Sucesso (204 No Content):** Nenhum corpo de resposta. O status indica que a exclusão foi bem-sucedida.
* **Resposta de Erro (404 Not Found):** Retornada se o endereço com o `id` informado não for encontrado.
<br/>

### Imagens de Produtos (`/images`)
Endpoint para fazer upload de imagens para os produtos. A URL base é `/images`.

#### 1. Upload de imagem
Associa uma imagem a um produto existente.
* **Método:** `POST`
* **URL:** `/images/add`
* **Tipo de Conteúdo:** `multipart/form-data`
* **Corpo da Requisição (Form-data):**
    * `file`: O arquivo de imagem (`.jpg`, `.png`, etc.).
    * `idProduto`: O ID do produto ao qual a imagem pertence.
    * `nomeDoArquivo`: Um nome customizado para o arquivo.
* **Resposta de Sucesso (200 OK):**
    ```text
    "Imagem salva com sucesso!"
    ```
* **Resposta de Erro (RuntimeException):** Se o produto com o `idProduto` informado não for encontrado.

#### 2. Upload de múltiplas imagens
Associa várias imagens a um único produto em uma única requisição.

* **Método:** `POST`
* **URL:** `/images/add/multiple`
* **Tipo de Conteúdo:** `multipart/form-data`
* **Corpo da Requisição (Form-data):**
    * `idProduto`: O ID do produto ao qual as imagens pertencem.
    * `files`: Os arquivos de imagem. Note que você deve usar a mesma chave (`files`) para cada arquivo que enviar.
* **Resposta de Sucesso (200 OK):**
    ```text
    "3 imagens salvas com sucesso!"
    ```
* **Respostas de Erro:**
    * **404 Not Found:** Se o produto com o `idProduto` informado não for encontrado.
    * **400 Bad Request:** Se nenhum arquivo for enviado na requisição.

#### 3. Excluir uma imagem
Remove uma imagem do banco de dados com base no seu ID.
* **Método:** `DELETE`
* **URL:** `/images/delete/{id}`
    * Substitua `{id}` pelo ID da imagem a ser excluída.
* **Resposta de Sucesso (204 No Content):** Nenhum corpo de resposta. O status indica que a exclusão foi bem-sucedida.
* **Resposta de Erro (404 Not Found):** Retornada se a imagem com o `id` informado não for encontrada.
=======
* **Resposta de Sucesso (200 OK):** Nenhum corpo de resposta (vazio). O status `200 OK` indica que a operação foi bem-sucedida.
* **Respostas de Erro:**
    * **400 Bad Request:** Se a quantidade a ser vendida for maior que o estoque disponível.
    * **404 Not Found:** Se o produto com o `id` informado não for encontrado.

>>>>>>> dbd70094e754c2fe4e568c7da2fa2ca4dd31df56
---

## 📝 Estrutura dos DTOs

<<<<<<< HEAD
Os Data Transfer Objects (DTOs) são usados para transferir dados entre o cliente e o servidor.

### DTOs de Produto
* **`ProdutoCreateDTO`**: Usado para criar um novo produto.
* **`ProdutoEditDTO`**: Usado para editar um produto existente.
* **`ProdutoCreateResponseDTO`**: Retornado após a criação ou edição de um produto.
* **`ProdutosResponseDTO`**: Usado na listagem de produtos.
* **`ProdutoVenderRequestDTO`**: Usado para especificar a quantidade a ser vendida.

### DTOs de Usuário
* **`UserCreateDTO`**: Usado para criar um novo usuário.
* **`UserResponseDTO`**: Usado na listagem de usuários.
* **`LoginRequestDTO`**: Usado para o processo de login.

### DTOs de Endereço
* **`EnderecoCreateDTO`**: Usado para criar ou editar um endereço.
* **`EnderecoResponseDTO`**: Retornado após a criação ou edição de um endereço.

### DTOs de Imagem
* **`ImagemProdutoCreateDTO`**: Usado para receber os dados do formulário de upload.
=======
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
>>>>>>> dbd70094e754c2fe4e568c7da2fa2ca4dd31df56
