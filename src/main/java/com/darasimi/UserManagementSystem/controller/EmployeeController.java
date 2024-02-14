package com.darasimi.UserManagementSystem.controller;
import java.security.Principal;
import java.util.List;

import com.darasimi.UserManagementSystem.dto.PasswordChangeForm;
import com.darasimi.UserManagementSystem.dto.UserDTO;
import com.darasimi.UserManagementSystem.entity.Employee;
import com.darasimi.UserManagementSystem.entity.User;
import com.darasimi.UserManagementSystem.repository.EmployeeRepository;
import com.darasimi.UserManagementSystem.repository.UserRepository;
import com.darasimi.UserManagementSystem.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean validateFields(Employee employee) {
        // Define regex patterns for each field
        String firstNameRegex = "^[a-zA-Z]+$"; // Example regex for first name (only letters)
        String lastNameRegex = "^[a-zA-Z]+$";  // Example regex for last name (only letters)
//        String emailRegex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$"; // Example regex for email address
//        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"; // Example regex for password (at least 8 characters, one uppercase, one lowercase, one number, one special character)

        // Check each field against its regex pattern
        return employee.getFirstName().matches(firstNameRegex) &&
                employee.getLastName().matches(lastNameRegex);
//                userDTO.getEmail().matches(emailRegex) &&
//                userDTO.getPassword().matches(passwordRegex);
    }

    // display list of employees
    @GetMapping("/employeeList")
    public String viewEmployeeList(Model model, Principal principal, @RequestParam(defaultValue = "1") int pageNo,
                                   @RequestParam(defaultValue = "firstName") String sortField,
                                   @RequestParam(defaultValue = "asc") String sortDir) {
        // Get the currently logged-in user's authentication details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Extract the role from the authentication details
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        // Fetch the appropriate employee list based on the role
        Page<Employee> page;
        if (role.equalsIgnoreCase("admin")) {
            // Fetch the page of employees created by the current user with pagination
            UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
            User user = userRepository.findByEmail(userDetails.getUsername());
            page = employeeService.getEmployeesCreatedBy(user, pageNo, 5, sortField, sortDir);
        } else {
            // Fetch all employees with pagination
            page = employeeService.getAllEmployees(pageNo, 5, sortField, sortDir);
        }

        // Add pagination attributes to the model
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        // Add the list of employees to the model
        model.addAttribute("listEmployees", page.getContent());

        // Add user details to the model
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        String rolePage = role.toLowerCase() + "-page";
        model.addAttribute("role", role);
        model.addAttribute("rolePage", rolePage);

        return "employeeList";
    }

    @GetMapping("/showNewEmployeeForm")
    public String showNewEmployeeForm(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().iterator().next().getAuthority(); // Assuming only one role per user
        String rolePage = role.toLowerCase() + "-page"; // Assuming role names are lowercase
        model.addAttribute("role", role);
        model.addAttribute("rolePage", rolePage);
        // create model attribute to bind form data
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "new_employee";
    }

    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee")Employee employee, Model model, BindingResult bindingResult) {
        // Check for validation errors
        if (bindingResult.hasErrors()) {
            return "new_employee";
        }

        // Validate the fields
        if (!validateFields(employee)) {
            model.addAttribute("errorMessage", "Please enter valid information for all fields");
            return "new_employee";
        }

        // Check if the employee already exists
        if (employeeService.employeeExists(employee.getEmail())) {
            model.addAttribute("errorMessage", "Employee with this email already exists");
            return "new_employee";
        }

        // Save the employee to the database
        employeeService.saveEmployee(employee);

        // Redirect to the employee list page
        return "redirect:/employeeList";
    }


//    @PostMapping("/saveEmployee")
//    public String saveEmployee(@ModelAttribute("employee")Employee employee, Model model, BindingResult bindingResult) {
//        if(bindingResult.hasErrors()){
//            return "new_employee";
//        }
//        if (!validateFields(employee)) {
//            model.addAttribute("errorMessage", "Please enter valid information for all fields");
//            return "new_employee";
//        }
//        if (employeeService.employeeExists(employee.getEmail())) {
//            model.addAttribute("errorMessage", "User with this email already exists");
//            return "new_employee";
//        }
//        // save employee to database
//        employeeService.saveEmployee(employee);
//        return "redirect:/employeeList";
//    }

    @GetMapping("/saveEmployee")
    public String saveEmployee1(Model model,Principal principal, @Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return "new_employee";
        }
        // save employee to database
        employeeService.saveEmployee(employee);
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().iterator().next().getAuthority(); // Assuming only one role per user
        String rolePage = role.toLowerCase() + "-page"; // Assuming role names are lowercase
        model.addAttribute("role", role);
        model.addAttribute("rolePage", rolePage);
        return "redirect:/employeeList";
    }

    @PostMapping("/updateEmployee")
    public String updateEmployee(@ModelAttribute("employee") Employee employee, Model model) {
        // Check if the email already exists for another employee
//        if (employeeService.employeeExistsExcludingCurrentEmployee(employee.getEmail(), employee.getId())) {
//            model.addAttribute("errorMessage", "Employee with this email already exists");
//            return "new_employee"; // Assuming this is the view for updating employee details
//        }

        // Update the employee details
        employeeService.saveEmployee(employee);

        // Redirect to a success page or return appropriate view
        return "redirect:/employeeList";
    }

    @GetMapping("/updateEmployee")
    public String updateEmployee(Model model,Principal principal, @Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return "new_employee";
        }
        // save employee to database
        employeeService.saveEmployee(employee);
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().iterator().next().getAuthority(); // Assuming only one role per user
        String rolePage = role.toLowerCase() + "-page"; // Assuming role names are lowercase
        model.addAttribute("role", role);
        model.addAttribute("rolePage", rolePage);
        return "redirect:/employeeList";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(Model model,Principal principal, @PathVariable ( value = "id") long id) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().iterator().next().getAuthority(); // Assuming only one role per user
        String rolePage = role.toLowerCase() + "-page"; // Assuming role names are lowercase
        model.addAttribute("role", role);
        model.addAttribute("rolePage", rolePage);
        // get employee from the service
        Employee employee = employeeService.getEmployeeById(id);

        // set employee as a model attribute to pre-populate the form
        model.addAttribute("employee", employee);
        return "update_employee";
    }

    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable (value = "id") long id) {

        // call delete employee method
        this.employeeService.deleteEmployeeById(id);
        return "redirect:/employeeList";
    }

    @GetMapping("/change-password")
    public String showChangePasswordForm(Model model) {
        model.addAttribute("passwordChangeForm", new PasswordChangeForm());
        return "changePassword";
    }

    @PostMapping("/change-password")
    public String changePassword(@ModelAttribute("passwordChangeForm") PasswordChangeForm form, Principal principal, Model model) {
        // Retrieve the authenticated employee
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Employee employee = employeeRepository.findByEmail(userDetails.getUsername());

        // Check if the current password matches the one saved in the database
        if (!passwordEncoder.matches(form.getCurrentPassword(), employee.getPassword())) {
            model.addAttribute("errorMessage", "Incorrect current password");
            return "changePassword";
        }

        if (!form.getNewPassword().equals(form.getConfirmPassword())) {
            model.addAttribute("errorMessage", "New password and confirm password must match");
            return "changePassword";
        }

        // Check if the new password is the same as the current password
        if (form.getNewPassword().equals(form.getCurrentPassword())) {
            model.addAttribute("errorMessage", "New password must be different from the current password");
            return "changePassword";
        }

        // Perform any additional validations here

        // Change the password
        employeeService.changePassword(employee, form.getNewPassword());
        model.addAttribute("message", "Password changed successfully");
        return "changePassword";
    }


    private Employee getCurrentEmployee(Principal principal) {
        String email = principal.getName();
        return employeeService.findByEmail(email);
    }
}
