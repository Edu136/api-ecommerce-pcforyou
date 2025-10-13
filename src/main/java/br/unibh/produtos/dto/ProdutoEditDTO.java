package br.unibh.produtos.dto;

import jakarta.validation.constraints.NotNull;

public record ProdutoEditDTO (
        String nome,
        String descricao,
        Double preco,
        Integer quantidade
){
}
