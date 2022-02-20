package com.kodilla.ecommercee.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService implements EmailSender {
    private final JavaMailSenderImpl mailSender;
    private static final String CONFIRMATION_SUBJECT = "Confirm your email";

    @Override
    public void send(String to, String email) {
        try {
            MimeMessage myMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(myMessage, "utf-8");
            helper.setText(email,true);
            helper.setTo(to);
            helper.setSubject(CONFIRMATION_SUBJECT);
            helper.setFrom("adminEmail");
            mailSender.send(myMessage);
        } catch (MessagingException | MailSendException e) {
            log.error("Failed to send email", e);
        }
    }
}
