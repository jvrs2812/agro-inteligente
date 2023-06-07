package com.agro.inteligente.Utils.Email;

public interface IEmailService {
    void enviarEmail(String destinatario, String assunto, String mensagem, String html);
}
