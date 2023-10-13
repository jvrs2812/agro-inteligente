package com.agro.inteligente.Enterprise.UseCase;

import com.agro.inteligente.Configuration.Security.JwtService;
import com.agro.inteligente.Email.Domain.EmailSaveDto;
import com.agro.inteligente.Email.Repository.Adapters.IAdapterEmailRepository;
import com.agro.inteligente.Enterprise.Domain.EnterpriseQrCodeDto;
import com.agro.inteligente.Enterprise.Domain.EnterpriseQrCodeReponseDto;
import com.agro.inteligente.Enterprise.Domain.EnterpriseResponseDto;
import com.agro.inteligente.Enterprise.Repository.Adapters.IAdapterEnterpriseRepository;
import com.agro.inteligente.Enterprise.Domain.EnterpriseDto;
import com.agro.inteligente.User.Domain.UserDto;
import com.agro.inteligente.User.Repository.Models.UserModelRepository;
import com.agro.inteligente.Utils.Commom.Archive.IArchive;
import com.agro.inteligente.Utils.Commom.Aws.IStorageAdapter;
import com.agro.inteligente.Utils.Commom.Exception.AgroException;
import com.agro.inteligente.Utils.Commom.QrCode.IQrCode;
import com.agro.inteligente.Utils.Commom.QrCode.MultipartImage;
import com.agro.inteligente.Utils.Commom.Validation;
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.agro.inteligente.Enterprise.Exception.EnterpriseException.*;

@Service
@RequiredArgsConstructor
public class EnterpriseCase {

    private final IAdapterEnterpriseRepository adapterEnterpriseRepository;

    private final JwtService jwtService;

    private final Validation validation;

    private final IAdapterEmailRepository emailRepository;

    private final IArchive archive;

    private final IQrCode qrCode;

    private final IStorageAdapter storageAdapter;

    private final EnterpriseValidation enterpriseValidation;

    @Value("${api.aws.bucket-image-qrcode}")
    private String bucket_qrcode;

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

    public EnterpriseQrCodeReponseDto generationQrCode(String enterprise_id) throws AgroException, IOException, WriterException {

        UserDto userLogged = this.jwtService.getUserContextSecurity().toDomain();

        this.enterpriseValidation.isValidEnterprise(enterprise_id, userLogged.getId().toString());

        this.logger.info("empresa encontrada, preparando para criar o qrcode");

        EnterpriseResponseDto enterpriseDto = this.adapterEnterpriseRepository.getMyEnterprise(userLogged.getId(), UUID.fromString(enterprise_id));

        EnterpriseQrCodeDto qrcode = this.adapterEnterpriseRepository.CreateQrCode(enterpriseDto);

        this.logger.info("Gerando QRCODE");

        MultipartFile[] image = new MultipartFile[]{
                this.qrCode.generate(qrcode.getQrcode_id(), 300)
        };
        this.logger.info("QRCODE GERADO");

        List<String> urls = this.storageAdapter.saveImage(image, bucket_qrcode);

        this.logger.info("ENVIADO PARA NUVEM");

        this.adapterEnterpriseRepository.updateUrlQrCode(urls.get(0), UUID.fromString(qrcode.getQrcode_id()));

        return EnterpriseQrCodeReponseDto
                .builder()
                .url(urls.get(0))
                .expired_at(qrcode.getExpiredAt())
                .build();
    }
}
