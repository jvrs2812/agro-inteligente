package com.ago.inteligente.User.UseCases;

import com.ago.inteligente.User.Domain.UserRegisterDto;
import com.ago.inteligente.User.Exception.UserExceptionEnum;
import com.ago.inteligente.User.Repository.IAdpterUserRepository;
import com.ago.inteligente.Utils.CaseUtils;
import com.ago.inteligente.Utils.Commom.Exception.AgroException;
import com.ago.inteligente.Utils.Commom.IValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.ago.inteligente.User.Exception.UserExceptionEnum.*;

@Service
@RequiredArgsConstructor
public class UserResgister {

    private final IAdpterUserRepository repository;

    private final CaseUtils utils;

    private final IValidation validation;

    public void Register(UserRegisterDto register) throws AgroException {

        if(this.repository.existEmail(register.getEmail()))
            throw new AgroException(EMAIL_EXIST);

        if(this.repository.existCpf(register.getCpf()))
            throw new AgroException(CPF_EXIST);

        if(!this.validation.isValidCpf(register.getCpf()))
            throw new AgroException(CPF_NOT_VALID);

        register.setPassword(this.utils.encodePassword(register.getPassword()));

        this.repository.save(register);
    }

}
