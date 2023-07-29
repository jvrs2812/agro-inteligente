package com.agro.inteligente.Email.Repository.Adapters;

import com.agro.inteligente.Email.Domain.EmailDto;
import com.agro.inteligente.Email.Domain.EmailSaveDto;

import java.util.List;
import java.util.UUID;

public interface IAdapterEmailRepository {
    List<EmailDto> getEmailsNotSend();

    void emailSend(UUID id);

    void saveEmail(EmailSaveDto emailSaveDto);
}
