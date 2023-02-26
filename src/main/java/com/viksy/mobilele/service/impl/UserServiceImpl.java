package com.viksy.mobilele.service.impl;

import com.viksy.mobilele.model.entity.UserEntity;
import com.viksy.mobilele.model.entity.UserRoleEntity;
import com.viksy.mobilele.model.entity.VerificationTokenEntity;
import com.viksy.mobilele.model.entity.enums.UserRoleEnum;
import com.viksy.mobilele.model.service.UserRegistrationServiceModel;
import com.viksy.mobilele.repository.UserRepository;
import com.viksy.mobilele.repository.UserRoleRepository;
import com.viksy.mobilele.service.EmailService;
import com.viksy.mobilele.service.UserService;
import com.viksy.mobilele.service.VerificationTokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenService verificationTokenService;
    private final EmailService emailService;

    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder, VerificationTokenService verificationTokenService, EmailService emailService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenService = verificationTokenService;
        this.emailService = emailService;
    }

    @Override
    public boolean authenticate(String username, String password) {
        Optional<UserEntity> userEntityOptional = userRepository.findByUsername(username);

        if(userEntityOptional.isEmpty()) {
            return false;
        } else {
            return passwordEncoder.matches(password, userEntityOptional.get().getPassword() );
        }
    }

    @Override
    public void registerUser(UserRegistrationServiceModel userRegistrationServiceModel) {
        UserRoleEntity userRole = userRoleRepository.findByRole(UserRoleEnum.USER);
        UserEntity newUser = new UserEntity();
        newUser.
                setUsername(userRegistrationServiceModel.getUsername()).
                setEmail(userRegistrationServiceModel.getEmail()).
                setFirstName(userRegistrationServiceModel.getFirstName()).
                setLastName(userRegistrationServiceModel.getLastName()).
                setPassword(passwordEncoder.encode(userRegistrationServiceModel.getPassword()))
                .setVerified(false).
                setRoles(Set.of(userRole));

        newUser = userRepository.save(newUser);

        String token = UUID.randomUUID().toString();
        verificationTokenService.saveVerificationToken(newUser, token);

        emailService.sendVerificationEmail(newUser);
    }

    @Override
    public boolean isUsernameFree(String username) {
        return userRepository.findByUsernameIgnoreCase(username).isEmpty();
    }

    @Override
    public String activateAccount(VerificationTokenEntity verificationToken) {
        String message;

        VerificationTokenEntity token = verificationTokenService.findByToken(verificationToken.getToken());

        if(token == null) {
            message = "Your verification token is invalid!";

            return message;
        }

        UserEntity userEntity = token.getUser();

        if(!userEntity.isVerified()) {
            Instant currentTime = Instant.now();

            if(currentTime.isAfter(token.getExpiry())) {
                message = "Your verification token has expired!";

                return message;
            }

            userEntity.setVerified(true);
            userEntity.setModified(Instant.now());

            updateUser(userEntity);

            message = "Your account has been verified!";

            return message;
        }

        message = "Your account has already been verified!";

        return message;
    }

    @Override
    public void updateUser(UserEntity userEntity) {
        userRepository.save(userEntity);
    }
}
