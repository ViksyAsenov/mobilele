package com.viksy.mobilele.service.impl;

import com.viksy.mobilele.model.entity.UserEntity;
import com.viksy.mobilele.model.entity.VerificationTokenEntity;
import com.viksy.mobilele.repository.VerificationTokenRepository;
import com.viksy.mobilele.service.VerificationTokenService;
import com.viksy.mobilele.web.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Clock;
import java.time.Instant;
import java.util.Calendar;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {
    private final int expiryOffset = 720;

    private final VerificationTokenRepository verificationTokenRepository;

    public VerificationTokenServiceImpl(VerificationTokenRepository verificationTokenRepository) {

        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Override
    @Transactional
    public VerificationTokenEntity findByToken(String token) {
        return verificationTokenRepository.findByToken(token).orElseThrow(() -> new ObjectNotFoundException("Token " + token + " was not found!"));
    }

    @Override
    @Transactional
    public VerificationTokenEntity findByUser(UserEntity userEntity) {
        return verificationTokenRepository.findByUser(userEntity).orElseThrow(() -> new ObjectNotFoundException("Token for user " + userEntity.getUsername() + " was not found!"));
    }

    @Override
    @Transactional
    public void saveVerificationToken(UserEntity userEntity, String token) {
        VerificationTokenEntity verificationTokenEntity = new VerificationTokenEntity()
                .setUser(userEntity)
                .setToken(token);

        verificationTokenEntity.setExpiry(calculateExpiryDate());

        verificationTokenRepository.save(verificationTokenEntity);
    }

    private Instant calculateExpiryDate() {
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.MINUTE, expiryOffset);

        return calendar.toInstant();
    }
}