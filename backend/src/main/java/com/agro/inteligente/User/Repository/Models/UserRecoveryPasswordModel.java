package com.agro.inteligente.User.Repository.Models;

import com.agro.inteligente.User.Domain.UserRecoveryPassword;
import com.agro.inteligente.User.Domain.UserResponseRecoveryPassword;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "recovery_password")
public class UserRecoveryPasswordModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",  updatable = false, unique = true, nullable = false)
    private UUID id;

    @NotNull
    private LocalDateTime expiredAt;

    @NotNull
    private boolean emailSend;

    public boolean isEmailSend() {
        return emailSend;
    }

    public void setEmailSend(boolean emailSend) {
        this.emailSend = emailSend;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    public UserResponseRecoveryPassword toDomain(){
        UserResponseRecoveryPassword dto = new UserResponseRecoveryPassword();
        dto.setId(this.id.toString());
        dto.setExpiredAt(this.expiredAt);

        return dto;
    }
}
