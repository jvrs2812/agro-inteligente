package com.agro.inteligente.User.Domain;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserDto {

    private UUID id;

    private String email;

    private String cpf;

    private String password;

    private String name;


}
