package org.piva.codelab.service;

import org.piva.codelab.model.User;
import org.piva.codelab.model.VerificationToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String fromAddress;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(User user, VerificationToken token) {
        String toEmail = user.getEmail();
        String subject = "Подтверждение регистрации";
        String confirmUrl = "http://localhost:8080/confirm?token=" + token.getToken();
        String message = "Здравствуйте, " +
                "\n\nДля подтверждения учетной записи перейдите по ссылке: "
                + confirmUrl +
                "\n\nСсылка действительна 15 минут.";

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(fromAddress);
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }
}
