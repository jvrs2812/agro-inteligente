package com.agro.inteligente.User.Domain;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserRefreshToken {

    @NotEmpty(message = "refreshToken não informado")
    private String refreshtoken;
}
