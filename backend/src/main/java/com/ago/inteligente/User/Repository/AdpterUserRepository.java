package com.ago.inteligente.User.Repository;


import com.ago.inteligente.User.Domain.UserRegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdpterUserRepository implements IAdpterUserRepository{

    @Autowired
    private IUserRepository repository;

    @Override
    public void save(UserRegisterDto register) {
        this.repository.save(UserModelRepository.toModel(register));
    }
}
