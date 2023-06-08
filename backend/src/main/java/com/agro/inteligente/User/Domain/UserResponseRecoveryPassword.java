package com.agro.inteligente.User.Domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseRecoveryPassword {

    private String id;

    private LocalDateTime expiredAt;

    private String email;

    private boolean emailSend;

    private boolean resetPassword;
}
