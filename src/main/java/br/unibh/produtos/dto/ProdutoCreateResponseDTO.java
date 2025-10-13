package br.unibh.produtos.dto;

import br.unibh.produtos.entity.ProdutoStatus;

public record ProdutoCreateResponseDTO(
        Long id,
        String nome,
        String descricao,
        Double preco,
        Integer quantidade,
        ProdutoStatus status
) {
}
