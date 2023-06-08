package com.agro.inteligente.User.Repository.Adapters;


import com.agro.inteligente.User.Domain.UserDto;
import com.agro.inteligente.User.Domain.UserRegisterDto;
import com.agro.inteligente.User.Repository.IUserRepository;
import com.agro.inteligente.User.Repository.Models.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AdapterUserRepository implements IAdapterUserRepository {

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

    @Override
    public void updatePasswordWithEmail(String email, String newPassword) {
        this.repository.updatePasswordWithId(email, newPassword);
    }
}
