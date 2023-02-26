package com.viksy.mobilele.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserRegistrationBindingModel {
    @NotBlank
    @Size(min=4, max=20)
    private String firstName;
    @NotBlank
    @Size(min=4, max=20)
    private String lastName;
    @NotBlank
    @Size(min=4, max=20)
    private String username;

    @NotBlank
    @Size(min=4)
    private String email;

    @NotBlank
    @Size(min=5)
    private String password;
    @NotBlank
    @Size(min=5)
    private String confirmPassword;

    public String getFirstName() {
        return firstName;
    }

    public UserRegistrationBindingModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRegistrationBindingModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserRegistrationBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegistrationBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegistrationBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegistrationBindingModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}
