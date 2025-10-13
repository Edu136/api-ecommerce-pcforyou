package br.unibh.produtos.dto;

import jakarta.validation.constraints.NotNull;

public record UserChangePasswordDTO (
        @NotNull(message = "A nova senha não pode ser nula.")
        String novaSenha
){
}
