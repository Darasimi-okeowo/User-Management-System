package com.darasimi.UserManagementSystem.controller;
import java.security.Principal;

import com.darasimi.UserManagementSystem.dto.UserDTO;
import com.darasimi.UserManagementSystem.dto.UserDTO1;
import com.darasimi.UserManagementSystem.entity.Employee;
import com.darasimi.UserManagementSystem.entity.User;
import com.darasimi.UserManagementSystem.repository.EmployeeRepository;
import com.darasimi.UserManagementSystem.repository.UserRepository;
import com.darasimi.UserManagementSystem.service.CustomUserDetailService;
import com.darasimi.UserManagementSystem.service.CustomUserDetails;
import com.darasimi.UserManagementSystem.service.UserService;
import com.darasimi.UserManagementSystem.service.UserService1;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    private CustomUserDetailService customUserDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserService1 userService1;
    @Autowired
    private EmployeeRepository employeeRepository;

    public boolean anyFieldEmpty(UserDTO userDTO) {
        return StringUtils.isEmpty(userDTO.getFirstName()) ||
                StringUtils.isEmpty(userDTO.getLastName()) ||
                StringUtils.isEmpty(userDTO.getEmail()) ||
                StringUtils.isEmpty(userDTO.getPassword()) ||
                StringUtils.isEmpty(userDTO.getConfirmPassword());
    }
    public boolean validateFields(UserDTO userDTO) {
        // Define regex patterns for each field
        String firstNameRegex = "^[a-zA-Z]+$"; // Example regex for first name (only letters)
        String lastNameRegex = "^[a-zA-Z]+$";  // Example regex for last name (only letters)
//        String emailRegex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$"; // Example regex for email address
//        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"; // Example regex for password (at least 8 characters, one uppercase, one lowercase, one number, one special character)

        // Check each field against its regex pattern
        return userDTO.getFirstName().matches(firstNameRegex) &&
                userDTO.getLastName().matches(lastNameRegex);
//                userDTO.getEmail().matches(emailRegex) &&
//                userDTO.getPassword().matches(passwordRegex);
    }

    @GetMapping("/registration")
    public String getRegistrationPage(){
        // create model object to store form data
//        UserDTO user = new UserDTO();
//        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/registration")
    public String saveUser(@ModelAttribute("user")UserDTO userDTO, Model model, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "register";
        }
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            model.addAttribute("confirmPasswordError", "Passwords do not match");
            return "register";
        }
        if (anyFieldEmpty(userDTO)) {
            model.addAttribute("errorMessage", "Please fill in all fields");
            return "register";
        }
        if (!validateFields(userDTO)) {
            model.addAttribute("errorMessage", "Please enter valid information for all fields");
            return "register";
        }
        if (userService.userExists(userDTO.getEmail())) {
            model.addAttribute("errorMessage", "User with this email already exists");
            return "register";
        }
        // Concatenate first name and last name to create full name
        String fullName = userDTO.getLastName() + " " + userDTO.getFirstName();
        userDTO.setFullName(fullName);
        userService.save(userDTO);
        model.addAttribute("message", "Registered successfully");
        return "register";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Assuming your login page template is named "login.html"
    }

//    @GetMapping("user-page")
//    public String userPage(Model model, Principal principal) {
//        CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(principal.getName());
//        String fullName = userDetails.getFullName();
//        model.addAttribute("fullName", fullName);
//        return "admin";
//    }

//    @GetMapping("user-page")
//    public String userPage (Model model, Principal principal) {
//        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
//        model.addAttribute("user", userDetails);
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String role = authentication.getAuthorities().iterator().next().getAuthority(); // Assuming only one role per user
//        String rolePage = role.toLowerCase() + "-page"; // Assuming role names are lowercase
//        model.addAttribute("role", role);
//        model.addAttribute("rolePage", rolePage);
//        return "user";
//    }
//
//    @GetMapping("admin-page")
//    public String adminPage (Model model, Principal principal) {
//        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
//        model.addAttribute("user", userDetails);
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String role = authentication.getAuthorities().iterator().next().getAuthority(); // Assuming only one role per user
//        String rolePage = role.toLowerCase() + "-page"; // Assuming role names are lowercase
//        model.addAttribute("role", role);
//        model.addAttribute("rolePage", rolePage);
//        return "admin";
//    }
//
//    @GetMapping("employee-page")
//    public String employeePage (Model model, Principal principal) {
//        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
//        model.addAttribute("user", userDetails);
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String role = authentication.getAuthorities().iterator().next().getAuthority(); // Assuming only one role per user
//        String rolePage = role.toLowerCase() + "-page"; // Assuming role names are lowercase
//        model.addAttribute("role", role);
//        model.addAttribute("rolePage", rolePage);
//        return "employee";
//    }
    @GetMapping("user-page")
    public String userPage(Model model, Principal principal) {
        setPageModelAttributes(model, principal);
        return "user";
    }

    @GetMapping("admin-page")
    public String adminPage(Model model, Principal principal) {
        setPageModelAttributes(model, principal);
        return "admin";
    }

    @GetMapping("employee-page")
    public String employeePage(Model model, Principal principal) {
        // Set common page attributes
        setPageModelAttributes(model, principal);

        // Retrieve the authenticated user
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());

        // Retrieve the employee details based on the email of the authenticated user
        Employee employee = employeeRepository.findByEmail(userDetails.getUsername());

        // Add employee details to the model
        model.addAttribute("employee", employee);
        return "employee";
    }

    private void setPageModelAttributes(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().iterator().next().getAuthority().toLowerCase();
        String rolePage = role + "-page";
        model.addAttribute("role", role);
        model.addAttribute("rolePage", rolePage);
    }
}