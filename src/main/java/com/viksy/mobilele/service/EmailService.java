package com.viksy.mobilele.service;

import com.viksy.mobilele.model.entity.UserEntity;
import org.apache.catalina.User;

import javax.mail.MessagingException;

public interface EmailService {
    //void sendHtmlMail(UserEntity userEntity);
    void sendVerificationEmail(UserEntity userEntity);
}
