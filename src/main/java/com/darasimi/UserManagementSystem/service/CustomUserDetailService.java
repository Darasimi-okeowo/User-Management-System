package com.darasimi.UserManagementSystem.service;

import com.darasimi.UserManagementSystem.entity.Employee;
import com.darasimi.UserManagementSystem.entity.User;
import com.darasimi.UserManagementSystem.repository.EmployeeRepository;
import com.darasimi.UserManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user != null) {
            return new CustomUserDetails(user);
        } else {
            // If user not found, try fetching employee by email
            Employee employee = employeeRepository.findByEmail(username);
            if (employee != null) {
                return new CustomUserDetails(employee);
            } else {
                // If neither user nor employee found, throw UsernameNotFoundException
                throw new UsernameNotFoundException("User or Employee not found");
            }
        }

//        User user = userRepository.findByEmail(username);
//        if(user == null){
//            throw new UsernameNotFoundException("User not found");
//        }
//        return new CustomUserDetails(user);
    }
}
