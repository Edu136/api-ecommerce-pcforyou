package br.unibh.produtos.dto;

public record SimpleProdutoDTO(
        Long id,
        String nome,
        Double preco,
        Integer quantidade
) {
}
