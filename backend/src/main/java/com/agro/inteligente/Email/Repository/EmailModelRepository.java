package com.agro.inteligente.Email.Repository;

import com.agro.inteligente.Email.Domain.EmailDto;
import com.agro.inteligente.Email.Domain.EmailSaveDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Table(name = "email_send")
@Entity
public class EmailModelRepository {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",  updatable = false, unique = true, nullable = false)
    private UUID id;

    @NotNull
    private String destiny;

    @NotNull
    private String subject;

    @NotNull
    private String message;

    @NotNull
    @Lob
    @Column(name = "html", length = 2000)
    private String html;

    @NotNull
    private boolean send;

    public static EmailModelRepository toModel(EmailSaveDto emailSaveDto){
        EmailModelRepository repository = new EmailModelRepository();
        repository.id = UUID.randomUUID();
        repository.destiny = emailSaveDto.getDestiny();
        repository.message = emailSaveDto.getMessage();
        repository.subject = emailSaveDto.getSubject();
        repository.html = emailSaveDto.getHtml();
        repository.send = false;

        return repository;
    }

    public EmailDto toDomain(){
        return EmailDto
                .builder()
                .id(this.id)
                .send(this.send)
                .destiny(this.destiny)
                .html(this.html)
                .message(this.message)
                .subject(this.subject)
                .build();
    }
}
