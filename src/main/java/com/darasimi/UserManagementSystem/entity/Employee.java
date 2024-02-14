package com.darasimi.UserManagementSystem.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;


    @Size(min = 2, message = "First name must not be less than 2 characters")
    @Pattern(regexp = "[A-Za-z]+", message = "First name must contain only alphabetic characters")
    @NotBlank(message = "Enter your First name")
    @Column(name = "first_name")
    private String firstName;

    @Size(min = 2, message = "Last name must not be less than 2 characters")
    @Pattern(regexp = "[A-Za-z]+", message = "Last name must contain only alphabetic characters")
    @NotBlank(message = "Enter your Last name")
    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "Enter your email")
    @Column(name = "email")
    @Email(message = "please provide a valid email address")
    private String email;

    @ManyToOne
    private User createdBy;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
