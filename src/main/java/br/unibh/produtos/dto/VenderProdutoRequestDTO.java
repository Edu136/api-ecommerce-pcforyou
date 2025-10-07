package br.unibh.produtos.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record  VenderProdutoRequestDTO (
        @NotNull(message = "A quantidade de produtos vendidos é obrigatória")
        @Positive(message = "A quantidade de produtos vendidos deve ser um número positivo")
        Integer quantidade
){
}
