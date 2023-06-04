package com.ago.inteligente.User.Repository;

import com.ago.inteligente.User.Domain.UserDto;
import com.ago.inteligente.User.Domain.UserRegisterDto;
import com.ago.inteligente.User.Domain.UserResponse;

import java.util.Optional;

public interface IAdpterUserRepository {
    UserDto save(UserRegisterDto register);

    Optional<UserDto> findByEmail(String email);

    boolean existEmail(String email);

    boolean existCpf(String cpf);
}
