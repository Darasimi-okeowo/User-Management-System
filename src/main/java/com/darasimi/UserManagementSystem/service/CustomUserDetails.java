package com.darasimi.UserManagementSystem.service;

import com.darasimi.UserManagementSystem.entity.Employee;
import com.darasimi.UserManagementSystem.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private User user;
    private Employee employee;
    public CustomUserDetails(User user) {
        this.user = user;
    }
    public CustomUserDetails(Employee employee) {
        this.employee = employee;
    }


//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(() -> user.getRole());
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (user != null) {
            authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        } else if (employee != null) {
            // Handle employee authorities if needed
            authorities.add(new SimpleGrantedAuthority("EMPLOYEE")); // Assuming a default role for employees
        }
        return authorities;
    }
    public String getFullName() {
        return user.getFullName();
    }

    @Override
    public String getPassword() {
        if (user != null) {
            return user.getPassword();
        } else if (employee != null) {
            // Implement logic to get password from employee entity
            return employee.getPassword();
        }
        return null;
    }

    @Override
    public String getUsername() {
        if (user != null) {
            return user.getEmail();
        } else if (employee != null) {
            // Implement logic to get username from employee entity
            return employee.getEmail();
        }
        return null;
    }
//    @Override
//    public String getPassword() {
//        return user.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return user.getEmail();
//    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

