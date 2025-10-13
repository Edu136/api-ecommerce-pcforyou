package br.unibh.produtos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UserCreateDTO(
        @NotNull(message = "Email não pode ser nulo")
        @Email(message = "Email inválido")
        String email,
        @NotNull(message = "Nome não pode ser nulo")
        String nome,
        @NotNull(message = "Senha não pode ser nulo")
        String senha
) {
}
