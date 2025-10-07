package br.unibh.produtos.dto;

import br.unibh.produtos.entity.ProdutoStatus;

import java.util.UUID;

public record ReponseCreateProdutoDTO(
        Long id,
        String nome,
        String descricao,
        Double preco,
        Integer quantidade,
        ProdutoStatus status
) {
}
