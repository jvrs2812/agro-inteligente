package com.agro.inteligente.User.Repository.Adapters;

import com.agro.inteligente.User.Domain.UserRecoveryPassword;
import com.agro.inteligente.User.Domain.UserResponseRecoveryPassword;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface IAdapterUserRecoveryPasswordRepository {
    Optional<UserResponseRecoveryPassword> findById(UUID id);

    UserResponseRecoveryPassword save(LocalDateTime expiredAt);
}
