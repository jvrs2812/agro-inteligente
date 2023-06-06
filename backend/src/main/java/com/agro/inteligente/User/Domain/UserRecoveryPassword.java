package com.agro.inteligente.User.Domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class UserRecoveryPassword {

    @NotEmpty(message = "email está vázio")
    @Email(message = "email inválido")
    private String email;
}
