package br.unibh.produtos.dto;

import java.util.List;

public record UserResponseDTO(
        Long id,
        String nome,
        String email,
        List<Long> idEndereco
) {
}
