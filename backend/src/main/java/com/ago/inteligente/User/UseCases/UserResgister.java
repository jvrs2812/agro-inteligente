package com.ago.inteligente.User.UseCases;

import com.ago.inteligente.User.Domain.UserRegisterDto;
import com.ago.inteligente.User.Repository.IAdpterUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserResgister {

    @Autowired
    private IAdpterUserRepository repository;

    public void Register(UserRegisterDto register){
        this.repository.save(register);
    }

}
