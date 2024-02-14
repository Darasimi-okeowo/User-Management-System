package com.darasimi.UserManagementSystem.dto;

import com.darasimi.UserManagementSystem.UserRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserDTO {
//    @NotBlank(message = "Enter your email")
//    @Email(message = "please provide a valid email address")
//    private String email;
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$\n", message = "Password should have atleast one lowercase letter,one uppercase letter, one digit, one special character and must be a minimum of 8 characters")
//    @NotBlank(message = "Enter your Password")
//    private String password;
//    @Enumerated(EnumType.STRING)
//    private UserRole role; // Define an enum for UserRole (e.g., USER, ADMIN)
//    @Pattern(regexp = "^(ADMIN|USER)$", message = "Input ADMIN OR USER")
//    private String role;


//    @Size(min = 2, message = "First name must not be less than 2 characters")
//    @Pattern(regexp = "[A-Za-z]+", message = "First name must contain only alphabetic characters")
//    @NotBlank(message = "Enter your First name")
//    private String firstname;
//
//    @Size(min = 2, message = "Last name must not be less than 2 characters")
//    @Pattern(regexp = "[A-Za-z]+", message = "Last name must contain only alphabetic characters")
//    @NotBlank(message = "Enter your Last name")
//    private String lastname;

    @NotBlank(message = "Enter your first name")
    private String firstName;
    @NotBlank(message = "Enter your last name")
    private String lastName;
    private String fullName;
    @NotBlank(message = "Enter your email")
    private String email;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @NotBlank(message = "Enter your password")
    private String password;
    @NotBlank(message = "Confirm your password")
    private String confirmPassword;



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
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public UserDTO(String firstName, String lastName, String fullName, String email, UserRole role, String password, String confirmPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.password = password;
        this.confirmPassword = confirmPassword;

    }
}

