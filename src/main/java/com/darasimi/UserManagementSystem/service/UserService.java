package com.darasimi.UserManagementSystem.service;

import com.darasimi.UserManagementSystem.dto.UserDTO;
import com.darasimi.UserManagementSystem.entity.User;

public interface UserService {
    User save (UserDTO userDTO);
    User findUserByEmail(String email);
    boolean userExists(String email);
}
