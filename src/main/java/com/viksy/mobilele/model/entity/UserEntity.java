package com.viksy.mobilele.model.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private boolean isVerified;
    private boolean isLocked;
    private String imageUrl;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<UserRoleEntity> roles = new HashSet<>();

    public String getUsername() {
        return username;
    }

    public UserEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public UserEntity setVerified(boolean verified) {
        isVerified = verified;
        return this;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public UserEntity setLocked(boolean locked) {
        isLocked = locked;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public UserEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Set<UserRoleEntity> getRoles() {
        return roles;
    }

    public UserEntity setRoles(Set<UserRoleEntity> userRoles) {
        this.roles = userRoles;
        return this;
    }

    public UserEntity addRole(UserRoleEntity userRole) {
        this.roles.add(userRole);
        return this;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "username='" + username + '\'' +
                ", password='" + email + '\'' +
                ", password='N/A" + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", isActive=" + isVerified +
                ", imageUrl='" + imageUrl + '\'' +
                ", userRoles=" + roles +
                '}';
    }
}
