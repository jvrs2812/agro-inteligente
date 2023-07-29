package com.agro.inteligente.Email;

import com.agro.inteligente.Email.Domain.EmailDto;
import com.agro.inteligente.Email.Repository.Adapters.IAdapterEmailRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailSchedule {
    Logger logger = LoggerFactory.getLogger(EmailSchedule.class);

    private final IAdapterEmailRepository adapterEmailRepository;

    private final IEmailService emailService;

    @Scheduled(cron = "0/30 * * * * *")
    private void sendEmail(){
        List<EmailDto> listNotSend = this.adapterEmailRepository.getEmailsNotSend();

        for (int i = 0; i < listNotSend.size(); i++) {
            try{
                this.emailService.enviarEmail(listNotSend.get(i).destiny, listNotSend.get(i).getSubject(), listNotSend.get(i).getMessage(), listNotSend.get(i).getHtml());
                this.adapterEmailRepository.emailSend(listNotSend.get(i).getId());
                this.logger.info("Email successfully sent " + listNotSend.get(i));
            }catch (RuntimeException e){
                this.logger.error("Error sending the e-mail | Error :  " + e.getMessage());
            }

        }
    }
}
