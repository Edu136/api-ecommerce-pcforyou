package br.unibh.produtos.dto;

import java.util.List;

public record PedidoResponseDTO(
        Long id,
        Long userId,
        String nomeUsuario,
        List<SimpleProdutoDTO> produtos,
        Double total
) {}