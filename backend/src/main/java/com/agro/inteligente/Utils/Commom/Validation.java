package com.agro.inteligente.Utils.Commom;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class Validation implements IValidation {

    private final CPFValidator cpfValidator;

    @Override
    public boolean isValidCpf(String cpf){
        try{
            this.cpfValidator.assertValid(cpf);
            return true;

        }catch (InvalidStateException e){
            return false;
        }
    }

    @Override
    public boolean isValidId(String id) {
        try{
            UUID.fromString(id);
            return true;
        }catch (IllegalArgumentException e){
            return false;
        }
    }


}
