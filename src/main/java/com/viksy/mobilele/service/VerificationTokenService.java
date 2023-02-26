package com.viksy.mobilele.service;

import com.viksy.mobilele.model.entity.UserEntity;
import com.viksy.mobilele.model.entity.VerificationTokenEntity;
import org.springframework.stereotype.Service;

@Service
public interface VerificationTokenService {
    VerificationTokenEntity findByToken(String token);
    VerificationTokenEntity findByUser(UserEntity userEntity);
    void saveVerificationToken(UserEntity userEntity, String token);
}
