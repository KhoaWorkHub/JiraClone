package com.khoa.managementsystem.service.Impl;

import com.khoa.managementsystem.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmailWithToken(String userEmail, String link) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        String subject = "Invitation to join project";
        String content = "You have been invited to join a project. Click the link below to join the project: " + link;

        try {
            helper.setTo(userEmail);
            helper.setText(content, true);
            helper.setSubject(subject);
            javaMailSender.send(mimeMessage);
        } catch (MailException | MessagingException e) {
            throw new MailException("Failed to send email") {
            };
        }
    }
}
