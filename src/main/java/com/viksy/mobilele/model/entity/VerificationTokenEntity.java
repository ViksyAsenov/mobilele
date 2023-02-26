package com.viksy.mobilele.model.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "verification_tokens")
public class VerificationTokenEntity extends BaseEntity {
    @OneToOne
    private UserEntity user;
    private String token;
    private Instant expiry;

    public UserEntity getUser() {
        return user;
    }

    public VerificationTokenEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public String getToken() {
        return token;
    }

    public VerificationTokenEntity setToken(String token) {
        this.token = token;
        return this;
    }

    public Instant getExpiry() {
        return expiry;
    }

    public VerificationTokenEntity setExpiry(Instant expiry) {
        this.expiry = expiry;
        return this;
    }
}
