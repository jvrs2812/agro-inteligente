package com.agro.inteligente.User.Repository.Adapters;

import com.agro.inteligente.User.Domain.UserRecoveryPassword;
import com.agro.inteligente.User.Domain.UserResponseRecoveryPassword;
import com.agro.inteligente.User.Repository.IUserRecoveryPasswordRepository;
import com.agro.inteligente.User.Repository.Models.UserRecoveryPasswordModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdapterUserRecoveryPasswordRepository implements IAdapterUserRecoveryPasswordRepository{

    private final IUserRecoveryPasswordRepository repository;

    @Override
    public Optional<UserResponseRecoveryPassword> findById(UUID id) {
        Optional<UserRecoveryPasswordModel> recoveryPassword = this.repository.findById(id);

        if(recoveryPassword.isEmpty())
            return Optional.empty();

        return Optional.of(recoveryPassword.get().toDomain());
    }

    @Override
    public UserResponseRecoveryPassword save(LocalDateTime expiredAt, String email) {
        UserRecoveryPasswordModel model = new UserRecoveryPasswordModel();

        model.setId(UUID.randomUUID());
        model.setExpiredAt(expiredAt);
        model.setEmailSend(false);
        model.setEmail(email);

        return this.repository.save(model).toDomain();
    }

    @Override
    public void updateSendEmail(UUID id) {
        this.repository.updateEmailSend(id);
    }

    @Override
    public void recoverySucess(UUID id) {
        this.repository.recoverySucess(id);
    }
}
