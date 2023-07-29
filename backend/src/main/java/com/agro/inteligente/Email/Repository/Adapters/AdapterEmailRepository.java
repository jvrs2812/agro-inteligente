package com.agro.inteligente.Email.Repository.Adapters;

import com.agro.inteligente.Email.Domain.EmailDto;
import com.agro.inteligente.Email.Domain.EmailSaveDto;
import com.agro.inteligente.Email.Repository.EmailModelRepository;
import com.agro.inteligente.Email.Repository.IEmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdapterEmailRepository implements IAdapterEmailRepository{

    private final IEmailRepository emailRepository;
    @Override
    public List<EmailDto> getEmailsNotSend() {
        List<EmailModelRepository> listaEmails = emailRepository.getAllEmailsNotSend();

        List<EmailDto> emailDtos = new ArrayList<EmailDto>();

        for (int i = 0; i < listaEmails.size(); i++) {
            emailDtos.add(listaEmails.get(i).toDomain());
        }

        return emailDtos;
    }

    @Override
    public void emailSend(UUID id) {
        this.emailRepository.emailSend(id);
    }

    @Override
    public void saveEmail(EmailSaveDto emailSaveDto) {
        this.emailRepository.save(EmailModelRepository.toModel(emailSaveDto));
    }
}
