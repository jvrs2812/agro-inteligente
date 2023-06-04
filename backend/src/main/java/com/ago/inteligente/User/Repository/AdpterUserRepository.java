package com.ago.inteligente.User.Repository;


import com.ago.inteligente.User.Domain.UserDto;
import com.ago.inteligente.User.Domain.UserRegisterDto;
import com.ago.inteligente.User.Domain.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdpterUserRepository implements IAdpterUserRepository{

    @Autowired
    private IUserRepository repository;

    @Override
    public UserDto save(UserRegisterDto register) {
        return this.repository.save(UserModelRepository.toModelRegister(register)).toDomain();
    }

    @Override
    public Optional<UserDto> findByEmail(String email) {
        Optional<UserModelRepository> user = this.repository.findByEmail(email);

        if(user.isEmpty())
            return Optional.empty();

        return Optional.of(user.get().toDomain());
    }

    @Override
    public boolean existEmail(String email) {

        Optional<UserDto> optional = this.findByEmail(email);

        return optional.isPresent();
    }

    @Override
    public boolean existCpf(String cpf) {
        return this.repository.existByCpf(cpf);
    }
}
