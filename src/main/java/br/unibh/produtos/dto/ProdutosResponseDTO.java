package br.unibh.produtos.dto;

import br.unibh.produtos.entity.ProdutoStatus;

import java.util.List;

public record ProdutosResponseDTO(
        Long id,
        String nome,
        String descricao,
        Double preco,
        Integer quantidade,
        ProdutoStatus status,
        List<Long> idImages,
        String nomeCategoria
) {
}
