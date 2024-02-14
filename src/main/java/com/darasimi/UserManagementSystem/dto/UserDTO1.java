package com.darasimi.UserManagementSystem.dto;

import com.darasimi.UserManagementSystem.UserRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class UserDTO1 {
    private String firstName;
    private String lastName;
    private String fullName;

    private String email;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserDTO1(String firstName, String lastName, String fullName, String email, UserRole role, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.password = password;
    }
}
