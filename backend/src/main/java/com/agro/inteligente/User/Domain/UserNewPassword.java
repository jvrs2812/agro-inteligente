package com.agro.inteligente.User.Domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserNewPassword {

    @NotEmpty(message = "password está nulo")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "minímo 8 caracteres e pelo menos um número e uma letra")
    private String password;

}
