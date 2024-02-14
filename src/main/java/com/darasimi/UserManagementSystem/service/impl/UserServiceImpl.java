package com.darasimi.UserManagementSystem.service.impl;

import com.darasimi.UserManagementSystem.dto.UserDTO;
import com.darasimi.UserManagementSystem.entity.User;
import com.darasimi.UserManagementSystem.repository.UserRepository;
import com.darasimi.UserManagementSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Override
    public User save(UserDTO userDTO) {
        User user = new User(
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getFullName(),
                userDTO.getEmail(),
                userDTO.getRole(),
                passwordEncoder.encode(userDTO.getPassword())
        );
        return userRepository.save(user);
    }
    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    @Override
    public boolean userExists(String email) {
        return userRepository.existsByEmail(email);
    }

//    private User convertToEntity(UserDTO userDTO) {
//        User user = new User();
//        user.setFullname(userDTO.getFullname());
//        user.setEmail(userDTO.getEmail());
//        // Set other properties as needed
//        return user;
//    }
}
