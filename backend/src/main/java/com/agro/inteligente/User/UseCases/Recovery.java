package com.agro.inteligente.User.UseCases;

import com.agro.inteligente.User.Domain.UserDto;
import com.agro.inteligente.User.Domain.UserRecoveryPassword;
import com.agro.inteligente.User.Domain.UserResponseRecoveryPassword;
import com.agro.inteligente.User.Repository.Adapters.IAdapterUserRecoveryPasswordRepository;
import com.agro.inteligente.User.Repository.Adapters.IAdapterUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Recovery {

    private IAdapterUserRecoveryPasswordRepository adapter;

    private IAdapterUserRepository adapterUser;

    public void recoveryPasswordWithEmail(UserRecoveryPassword recoveryPassword){
        Optional<UserDto> user = this.adapterUser.findByEmail(recoveryPassword.getEmail());

        if(user.isEmpty())
            return;


    }
}
