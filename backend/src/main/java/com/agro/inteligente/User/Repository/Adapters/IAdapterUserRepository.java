package com.agro.inteligente.User.Repository.Adapters;

import com.agro.inteligente.User.Domain.UserDto;
import com.agro.inteligente.User.Domain.UserRegisterDto;

import java.util.Optional;
import java.util.UUID;

public interface IAdapterUserRepository {
    UserDto save(UserRegisterDto register);

    Optional<UserDto> findByEmail(String email);

    boolean existEmail(String email);

    boolean existCpf(String cpf);

    void updatePasswordWithEmail(String email, String newPassword);
}
