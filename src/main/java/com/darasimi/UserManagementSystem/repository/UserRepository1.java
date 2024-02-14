package com.darasimi.UserManagementSystem.repository;

import com.darasimi.UserManagementSystem.entity.User;
import com.darasimi.UserManagementSystem.entity.User1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository1 extends JpaRepository<User1, Long> {
    User1 findByEmail(String email);
}
