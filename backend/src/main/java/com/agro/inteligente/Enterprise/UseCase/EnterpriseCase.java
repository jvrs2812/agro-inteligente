package com.agro.inteligente.Enterprise.UseCase;

import com.agro.inteligente.Configuration.Security.JwtService;
import com.agro.inteligente.Enterprise.Repository.Adapters.IAdapterEnterpriseRepository;
import com.agro.inteligente.Enterprise.Domain.EnterpriseDto;
import com.agro.inteligente.User.Repository.Models.UserModelRepository;
import com.agro.inteligente.Utils.Commom.Archive.IArchive;
import com.agro.inteligente.Utils.Commom.Exception.AgroException;
import com.agro.inteligente.Utils.Commom.Validation;
import com.agro.inteligente.Utils.Email.IEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.agro.inteligente.Enterprise.Exception.EnterpriseException.CNPJ_ALREADY_EXIST;
import static com.agro.inteligente.Enterprise.Exception.EnterpriseException.CNPJ_NOT_VALID;

@Service
@RequiredArgsConstructor
public class EnterpriseCase {

    private final IAdapterEnterpriseRepository adapterEnterpriseRepository;

    private final JwtService jwtService;

    private final Validation validation;

    private final IEmailService emailService;

    private final IArchive archive;

    public void CreateEnterprise(EnterpriseDto enterpriseDto) throws AgroException {
        if(!this.validation.isValidCNPJ(enterpriseDto.getCnpj()))
            throw new AgroException(CNPJ_NOT_VALID);

        if(this.adapterEnterpriseRepository.existCnpj(enterpriseDto.getCnpj()))
            throw new AgroException(CNPJ_ALREADY_EXIST);

        UserModelRepository userLogged = this.jwtService.getUserContextSecurity();

        this.adapterEnterpriseRepository.CreateEnterprise(enterpriseDto, userLogged.toDomain());

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("nome_empresa", enterpriseDto.getName_fancy());

        String html = this.archive.alterArchive("new_enterprise_create", map);

        this.emailService.enviarEmail(userLogged.getEmail(), "Empresa Criada com sucesso", "", html);
    }
}
