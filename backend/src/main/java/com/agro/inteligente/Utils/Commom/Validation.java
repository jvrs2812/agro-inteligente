package com.agro.inteligente.Utils.Commom;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
