package com.darasimi.UserManagementSystem.service.impl;

import com.darasimi.UserManagementSystem.entity.Employee;
import com.darasimi.UserManagementSystem.entity.User;
import com.darasimi.UserManagementSystem.repository.EmployeeRepository;
import com.darasimi.UserManagementSystem.repository.UserRepository;
import com.darasimi.UserManagementSystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

//    @Override
//    public void saveEmployee(Employee employee) {
//        // Get the username of the currently authenticated user
//        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
//
//        // Find the User entity corresponding to the authenticated user
//        User loggedInUser = userRepository.findByEmail(loggedInUsername);
//
//        // Set the createdBy field of the employee to the authenticated user
//        employee.setCreatedBy(loggedInUser);
//        // Encrypt password before saving
//        String encryptedPassword = passwordEncoder.encode(employee.getPassword());
//        employee.setPassword(encryptedPassword);
//
//        employeeRepository.save(employee);
//    }

    @Override
    public void saveEmployee(Employee employee) {
        // Get the username of the currently authenticated user
        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        // Find the User entity corresponding to the authenticated user
        User loggedInUser = userRepository.findByEmail(loggedInUsername);

        // Set the createdBy field of the employee to the authenticated user
        employee.setCreatedBy(loggedInUser);

        // Encrypt default password ("password")
        String defaultPassword = "password";
        String encryptedDefaultPassword = passwordEncoder.encode(defaultPassword);

        // Set default password if the password is not provided
        if (employee.getPassword() == null || employee.getPassword().isEmpty()) {
            employee.setPassword(encryptedDefaultPassword);
        } else {
            // Encrypt provided password before saving
            String encryptedPassword = passwordEncoder.encode(employee.getPassword());
            employee.setPassword(encryptedPassword);
        }
        employeeRepository.save(employee);
    }

    @Override
    public boolean employeeExists(String email) {
        return userRepository.existsByEmail(email);
    }

//    @Override
//    public boolean employeeExistsExcludingCurrentEmployee(String email, Long employeeId) {
//        Employee existingEmployee = employeeRepository.findByEmail(email);
//        return existingEmployee != null && existingEmployee.getId() == employeeId;
//    }

    @Override
    public Employee getEmployeeById(long id) {
        Optional<Employee> optional = employeeRepository.findById(id);
        Employee employee = null;
        if (optional.isPresent()) {
            employee = optional.get();
        } else {
            throw new RuntimeException(" Employee not found for id :: " + id);
        }
        return employee;
    }

    @Override
    public void deleteEmployeeById(long id) {
        this.employeeRepository.deleteById(id);
    }

//    @Override
//    public Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
//        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
//                Sort.by(sortField).descending();
//
//        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
//        return this.employeeRepository.findAll(pageable);
//    }

    @Override
    public Page<Employee> getEmployeesCreatedBy(User user, int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return employeeRepository.findByCreatedBy(user, pageable);
    }

    @Override
    public Page<Employee> getAllEmployees(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return employeeRepository.findAll(pageable);
    }

    @Override
    public void changePassword(Employee employee, String newPassword) {
        employee.setPassword(passwordEncoder.encode(newPassword));
        employeeRepository.save(employee);
    }
}
