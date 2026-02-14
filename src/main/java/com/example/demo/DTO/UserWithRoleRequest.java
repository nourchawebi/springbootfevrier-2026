package com.example.demo.DTO;

import com.example.demo.entities.Role;
import com.example.demo.entities.RoleName;
import com.example.demo.entities.UserEntity;

public class UserWithRoleRequest {
    private UserEntity user;
    private Role role;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
