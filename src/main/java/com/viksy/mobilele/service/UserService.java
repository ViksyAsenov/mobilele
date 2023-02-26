package com.viksy.mobilele.service;

import com.viksy.mobilele.model.entity.UserEntity;
import com.viksy.mobilele.model.entity.VerificationTokenEntity;
import com.viksy.mobilele.model.service.UserRegistrationServiceModel;

import javax.mail.MessagingException;

public interface UserService {
    boolean authenticate(String username, String password);
    void registerUser(UserRegistrationServiceModel userRegistrationServiceModel);
    boolean isUsernameFree(String username);
    String activateAccount(VerificationTokenEntity verificationTokenEntity);

    void updateUser(UserEntity userEntity);
}
