package com.darasimi.UserManagementSystem.service;

import com.darasimi.UserManagementSystem.dto.UserDTO1;
import com.darasimi.UserManagementSystem.entity.User;
import com.darasimi.UserManagementSystem.entity.User1;

public interface UserService1 {
    User1 save1 (UserDTO1 userDTO1);
    User1 findUserByEmail1(String email);
}
