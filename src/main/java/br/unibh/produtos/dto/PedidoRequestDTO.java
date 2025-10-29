package br.unibh.produtos.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record PedidoRequestDTO(
        @NotNull Long userId,
        @NotEmpty List<ItemPedidoRequestDTO> itens
) {}