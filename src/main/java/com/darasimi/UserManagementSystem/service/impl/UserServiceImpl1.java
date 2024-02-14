package com.darasimi.UserManagementSystem.service.impl;

import com.darasimi.UserManagementSystem.dto.UserDTO1;
import com.darasimi.UserManagementSystem.entity.User1;
import com.darasimi.UserManagementSystem.repository.UserRepository1;
import com.darasimi.UserManagementSystem.service.UserService1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl1 implements UserService1 {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository1 userRepository;
    @Override
    public User1 save1(UserDTO1 userDTO1) {
        User1 user = new User1(
                userDTO1.getFirstName(),
                userDTO1.getLastName(),
                userDTO1.getFullName(),
                userDTO1.getEmail(),
                userDTO1.getRole(),
                passwordEncoder.encode(userDTO1.getPassword())
        );
        return userRepository.save(user);
    }
    @Override
    public User1 findUserByEmail1(String email) {
        return userRepository.findByEmail(email);
    }
}
