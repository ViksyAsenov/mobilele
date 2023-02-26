package com.viksy.mobilele.service.impl;

import com.viksy.mobilele.model.entity.UserEntity;
import com.viksy.mobilele.model.entity.VerificationTokenEntity;
import com.viksy.mobilele.service.EmailService;
import com.viksy.mobilele.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    private final VerificationTokenService verificationTokenService;
    @Autowired
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;

    public EmailServiceImpl(VerificationTokenService verificationTokenService, JavaMailSender javaMailSender) {
        this.verificationTokenService = verificationTokenService;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendVerificationEmail(UserEntity userEntity) {
        try {
            VerificationTokenEntity verificationTokenEntity = verificationTokenService.findByUser(userEntity);

            if(verificationTokenEntity != null) {
                String token = verificationTokenEntity.getToken();

                SimpleMailMessage mailMessage = new SimpleMailMessage();

                mailMessage.setFrom(sender);
                mailMessage.setTo(userEntity.getEmail());
                mailMessage.setSubject("Email Verification From Mobilele");
                mailMessage.setText("Thanks for signing up! Please click here to verify: http://localhost:8080/activation?token=" + token);

                javaMailSender.send(mailMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
