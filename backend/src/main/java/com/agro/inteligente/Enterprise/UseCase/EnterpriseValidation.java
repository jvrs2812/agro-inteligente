package com.agro.inteligente.Enterprise.UseCase;

import com.agro.inteligente.Enterprise.Repository.Adapters.IAdapterEnterpriseRepository;
import com.agro.inteligente.Utils.Commom.Exception.AgroException;
import com.agro.inteligente.Utils.Commom.Validation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.agro.inteligente.Enterprise.Exception.EnterpriseException.ID_ENTERPRISE_IS_INVALID;

@Component
@RequiredArgsConstructor
public class EnterpriseValidation {

    private final IAdapterEnterpriseRepository adapterEnterpriseRepository;

    private final Validation validation;

    private Logger logger = LoggerFactory.getLogger(EnterpriseValidation.class);
    public void isValidEnterprise(String id_enterprise, String id_user) throws AgroException {

        if(!this.adapterEnterpriseRepository.existEntepriseForThisUser(UUID.fromString(id_user), UUID.fromString(id_enterprise))){
            this.logger.info("ID ENTERPRISE INFORMADO É INVÁLIDO");
            throw new AgroException(ID_ENTERPRISE_IS_INVALID);
        }

        if(!this.validation.isValidUUID(id_enterprise)){
            this.logger.info("ID ENTERPRISE INFORMADO É INVÁLIDO");
            throw new AgroException(ID_ENTERPRISE_IS_INVALID);
        }

    }
}
