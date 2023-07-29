package com.agro.inteligente.Email.Domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailSaveDto {
    @Email
    public String destiny;

    @NotNull
    private String subject;

    @NotNull
    private String message;

    @NotNull
    private String html;

}
