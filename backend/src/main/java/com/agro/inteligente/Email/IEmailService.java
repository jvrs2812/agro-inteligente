package com.agro.inteligente.Email;

public interface IEmailService {
    void enviarEmail(String destinatario, String assunto, String mensagem, String html) throws RuntimeException;
}
