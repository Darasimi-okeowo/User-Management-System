package com.darasimi.UserManagementSystem.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;

@Service
public class CustomSuccessHandler implements AuthenticationSuccessHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomSuccessHandler.class);
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // Log authentication details for debugging
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        logger.info("User '{}' has been successfully authenticated. Authorities: {}",
                userDetails.getUsername(), userDetails.getAuthorities());


        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.stream().map(GrantedAuthority::getAuthority).findFirst().orElse("");

        switch (role) {
            case "ADMIN":
                response.sendRedirect("/admin-page");
                break;
            case "USER":
                response.sendRedirect("/user-page");
                break;
            case "EMPLOYEE":
                response.sendRedirect("/employee-page");
                break;
            default:
                response.sendRedirect("/error");
                break;
        }
    }
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//                                        Authentication authentication) throws IOException, ServletException {
//        // TODO Auto-generated method stub
//
//        var authorities = authentication.getAuthorities();
//        var roles = authorities.stream().map(r -> r.getAuthority()).findFirst();
//
//        if (roles.orElse("").equals("ADMIN")) {
//            response.sendRedirect("/admin-page");
//        } else if (roles.orElse("").equals("USER")) {
//            response.sendRedirect("/user-page");
//        } else if (roles.orElse("").equals("EMPLOYEE")) {
//            response.sendRedirect("/employee-page");
//        } else {
//            response.sendRedirect("/error");
//        }
//    }
}