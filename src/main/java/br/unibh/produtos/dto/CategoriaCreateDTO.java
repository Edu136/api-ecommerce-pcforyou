package br.unibh.produtos.dto;

import jakarta.validation.constraints.NotNull;

public record CategoriaCreateDTO(
        @NotNull(message = "Nome da categoria não pode ser nulo")
        String nomeCategoria
) {
}
