package br.unibh.produtos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record LoginRequestDTO(
                @NotNull(message = "O campo do email não pode ser nulo.")
                @Email(message = "Digite um email válido.")
                String email,
                @NotNull(message = "A senha não pode ser vazia")
                String senha
        ) {
}
