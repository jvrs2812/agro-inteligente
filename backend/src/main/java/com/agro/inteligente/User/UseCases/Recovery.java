package com.agro.inteligente.User.UseCases;

import com.agro.inteligente.User.Domain.UserDto;
import com.agro.inteligente.User.Domain.UserRecoveryPassword;
import com.agro.inteligente.User.Domain.UserResponseRecoveryPassword;
import com.agro.inteligente.User.Repository.Adapters.IAdapterUserRecoveryPasswordRepository;
import com.agro.inteligente.User.Repository.Adapters.IAdapterUserRepository;
import com.agro.inteligente.Utils.Commom.Archive.IArchive;
import com.agro.inteligente.Utils.Email.IEmailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class Recovery {

    private Logger logger = LoggerFactory.getLogger(Recovery.class);

    private final IAdapterUserRecoveryPasswordRepository adapter;

    private final IAdapterUserRepository adapterUser;

    private final IEmailService emailService;

    private final IArchive archive;

    @Value("${url_producao}")
    private String url_producao;

    public void recoveryPasswordWithEmail(UserRecoveryPassword recoveryPassword){
        Optional<UserDto> user = this.adapterUser.findByEmail(recoveryPassword.getEmail());

        if(user.isEmpty())
            return;

        UserResponseRecoveryPassword recoveryPasswordSaved = this.adapter.save(LocalDateTime.now().minusMinutes(15));

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("url",this.url_producao+"/"+recoveryPasswordSaved.getId().toString());

        try{
            this.emailService.enviarEmail(recoveryPassword.getEmail(), "Recuperação de senha", "", this.archive.alterArchive("recovery_password", map));
            this.adapter.updateSendEmail(UUID.fromString(recoveryPasswordSaved.getId()));
            this.logger.info("Email de recovery de senha foi enviado. id "+recoveryPasswordSaved.getId());
        }catch (RuntimeException e){
            logger.error("Email falhou para enviar id: "+recoveryPasswordSaved.getId());
        }
    }
}
