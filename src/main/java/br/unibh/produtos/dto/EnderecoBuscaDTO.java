package br.unibh.produtos.dto;

import jakarta.validation.constraints.NotNull;

public record EnderecoBuscaDTO (
        @NotNull(message = "O id do user é obrigatório.")
        Long Userid
){
}
