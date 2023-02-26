package com.viksy.mobilele.model.entity;

import com.viksy.mobilele.model.entity.enums.UserRoleEnum;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class UserRoleEntity extends BaseEntity{
    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    private UserRoleEnum role;

    public UserRoleEnum  getRole() {
        return role;
    }

    public UserRoleEntity setRole(UserRoleEnum role) {
        this.role = role;
        return this;
    }
}
