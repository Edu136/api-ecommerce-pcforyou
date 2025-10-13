package br.unibh.produtos.dto;

import jakarta.validation.constraints.NotNull;

public record ImagemProdutoCreateDTO(
        @NotNull(message = "Id do produto não pode ser nulo.")
        Long idProduto,
        @NotNull(message = "Nome do produto não pode ser nulo.")
        String nomeDoArquivo
) {
}
