package br.unibh.produtos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemPedidoRequestDTO(
        @NotNull Long produtoId,
        @NotNull @Min(1) Integer quantidade
) {}