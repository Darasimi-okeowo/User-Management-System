//package com.darasimi.UserManagementSystem.config;
//import com.darasimi.UserManagementSystem.entity.UserDtls;
//import com.darasimi.UserManagementSystem.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService{
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//
//        UserDtls user = userRepository.findByEmail(email);
//
//        if (user != null) {
//            return new CustomUserDetails(user);
//        }
//
//        throw new UsernameNotFoundException("user not available");
//    }
//}
