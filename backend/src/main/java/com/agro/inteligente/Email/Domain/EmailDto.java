package com.agro.inteligente.Email.Domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class EmailDto{
    @NotNull
    private UUID id;

    @Email
    public String destiny;

    @NotNull
    private String subject;

    @NotNull
    private String message;

    @NotNull
    private String html;

    @NotNull
    private boolean send;

}
