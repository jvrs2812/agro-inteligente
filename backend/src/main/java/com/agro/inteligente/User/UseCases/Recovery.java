package com.agro.inteligente.User.UseCases;

import com.agro.inteligente.Email.Domain.EmailSaveDto;
import com.agro.inteligente.Email.Repository.Adapters.IAdapterEmailRepository;
import com.agro.inteligente.User.Domain.UserDto;
import com.agro.inteligente.User.Domain.UserRecoveryPassword;
import com.agro.inteligente.User.Domain.UserResponseRecoveryPassword;
import com.agro.inteligente.User.Repository.Adapters.IAdapterUserRecoveryPasswordRepository;
import com.agro.inteligente.User.Repository.Adapters.IAdapterUserRepository;
import com.agro.inteligente.Utils.CaseUtils;
import com.agro.inteligente.Utils.Commom.Archive.IArchive;
import com.agro.inteligente.Utils.Commom.Exception.AgroException;
import com.agro.inteligente.Utils.Commom.IValidation;
import com.agro.inteligente.Email.IEmailService;
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

import static com.agro.inteligente.User.Exception.UserExceptionEnum.*;

@Service
@RequiredArgsConstructor
public class Recovery {

    private Logger logger = LoggerFactory.getLogger(Recovery.class);

    private final IAdapterUserRecoveryPasswordRepository adapter;

    private final IAdapterUserRepository adapterUser;

    private final IArchive archive;

    private final IValidation validation;

    private final CaseUtils caseUtils;

    private final IAdapterEmailRepository emailRepository;

    @Value("${url_producao}")
    private String url_producao;

    public void recoveryPasswordWithEmail(UserRecoveryPassword recoveryPassword){
        Optional<UserDto> user = this.adapterUser.findByEmail(recoveryPassword.getEmail());

        if(user.isEmpty())
            return;

        UserResponseRecoveryPassword recoveryPasswordSaved = this.adapter.save(LocalDateTime.now().plusMinutes(15), recoveryPassword.getEmail());

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("url",this.url_producao+"/api/v1/auth/recovery-password/"+recoveryPasswordSaved.getId().toString());


        this.emailRepository.saveEmail(
                EmailSaveDto
                        .builder()
                        .destiny(recoveryPassword.getEmail())
                        .subject("Recuperação de senha")
                        .message("")
                        .html(this.archive.alterArchive("recovery_password", map))
                        .build()
            );

        this.adapter.updateSendEmail(UUID.fromString(recoveryPasswordSaved.getId()));
        this.logger.info("Email de recovery de senha foi agendado. id "+recoveryPasswordSaved.getId());
    }

    public void recoveryPasswordWithId(String id, String newPassword) throws AgroException {
        if(this.validation.isValidId(id)){
            Optional<UserResponseRecoveryPassword> recoveryPasswordOptional = this.adapter.findById(UUID.fromString(id));

            if(recoveryPasswordOptional.isEmpty())
                throw new AgroException(ID_IS_NOT_VALID);

            UserResponseRecoveryPassword recoveryPassword = recoveryPasswordOptional.get();

            if(recoveryPassword.getExpiredAt().isBefore(LocalDateTime.now()))
                throw new AgroException(RECOVERY_IS_EXPIRED);

            if(recoveryPassword.isResetPassword())
                throw new AgroException(RECOVERY_IS_UTILIZED);

            Optional<UserDto> user = this.adapterUser.findByEmail(recoveryPassword.getEmail());

            if(user.isEmpty())
                throw new AgroException(USER_NOT_EXIST);

            this.adapterUser.updatePasswordWithEmail(user.get().getEmail(), this.caseUtils.encodePassword(newPassword));

            this.adapter.recoverySucess(UUID.fromString(id));
        }else{
            throw new AgroException(ID_IS_NOT_VALID);
        }
    }
}
