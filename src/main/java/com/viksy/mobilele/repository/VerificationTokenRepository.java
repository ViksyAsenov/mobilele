package com.viksy.mobilele.repository;

import com.viksy.mobilele.model.entity.UserEntity;
import com.viksy.mobilele.model.entity.VerificationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationTokenEntity, Long> {
    Optional<VerificationTokenEntity> findByToken(String token);
    Optional<VerificationTokenEntity> findByUser(UserEntity userEntity);
}
