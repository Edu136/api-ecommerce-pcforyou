package br.unibh.produtos.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateProdutoRequestDTO(
        @NotNull(message = "O nome do produto é obrigatório")
        String nome,
        @NotNull(message = "A descrição do produto é obrigatória")
        String descricao,
        @NotNull(message = "O preço do produto é obrigatório")
        @Positive(message = "O preço do produto deve ser positivo")
        Double preco,
        @NotNull(message = "A quantidade do produto é obrigatória")
        @Positive(message = "A quantidade do produto deve ser positiva")
        Integer quantidade
        ){
}
