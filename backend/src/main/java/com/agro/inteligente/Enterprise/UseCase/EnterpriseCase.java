package com.agro.inteligente.Enterprise.UseCase;

import com.agro.inteligente.Configuration.Security.JwtService;
import com.agro.inteligente.Email.Domain.EmailSaveDto;
import com.agro.inteligente.Email.Repository.Adapters.IAdapterEmailRepository;
import com.agro.inteligente.Enterprise.Domain.EnterpriseResponseDto;
import com.agro.inteligente.Enterprise.Repository.Adapters.IAdapterEnterpriseRepository;
import com.agro.inteligente.Enterprise.Domain.EnterpriseDto;
import com.agro.inteligente.User.Repository.Models.UserModelRepository;
import com.agro.inteligente.User.UseCases.Recovery;
import com.agro.inteligente.Utils.Commom.Archive.IArchive;
import com.agro.inteligente.Utils.Commom.Exception.AgroException;
import com.agro.inteligente.Utils.Commom.Validation;
import com.agro.inteligente.Email.IEmailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.agro.inteligente.Enterprise.Exception.EnterpriseException.CNPJ_ALREADY_EXIST;
import static com.agro.inteligente.Enterprise.Exception.EnterpriseException.CNPJ_NOT_VALID;

@Service
@RequiredArgsConstructor
public class EnterpriseCase {

    private final IAdapterEnterpriseRepository adapterEnterpriseRepository;

    private final JwtService jwtService;

    private final Validation validation;

    private final IAdapterEmailRepository emailRepository;

    private final IArchive archive;

    private Logger logger = LoggerFactory.getLogger(EnterpriseCase.class);

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

        this.emailRepository.saveEmail(
                EmailSaveDto
                        .builder()
                        .destiny(userLogged.getEmail())
                        .subject("Empresa Criada com sucesso")
                        .message("")
                        .html(html)
                        .build()
        );

        this.logger.info("Email de empresa criada foi agendado. email a ser enviado = " + userLogged.getEmail());

    }

    public List<EnterpriseResponseDto> getMyEnterprise(){
        return this.adapterEnterpriseRepository.getMyEnterprise(this.jwtService.getUserContextSecurity().getId());
    }
}
