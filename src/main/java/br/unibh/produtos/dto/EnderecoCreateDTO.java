package br.unibh.produtos.dto;

import jakarta.validation.constraints.NotNull;

public record EnderecoCreateDTO(
        @NotNull(message = "O CEP é obrigatório")
        String cep,
        @NotNull(message = "O logradouro é obrigatório")
        String logradouro,
        String complemento,
        @NotNull(message = "O número é obrigatório")
        String numero,
        @NotNull(message = "O bairro é obrigatório")
        String bairro,
        @NotNull(message = "A cidade é obrigatória")
        String cidade,
        @NotNull(message = "O estado é obrigatório")
        String estado,
        @NotNull(message = "O usuário é obrigatório")
        Long userId
){
}
