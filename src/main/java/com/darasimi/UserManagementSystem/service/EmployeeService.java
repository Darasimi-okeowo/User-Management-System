package com.darasimi.UserManagementSystem.service;

import com.darasimi.UserManagementSystem.entity.Employee;
import com.darasimi.UserManagementSystem.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {
    Employee findByEmail(String email);
    List<Employee> getAllEmployees();
    void saveEmployee(Employee employee);
    boolean employeeExists(String email);
//    boolean employeeExistsExcludingCurrentEmployee(String email, Long employeeId);
    Employee getEmployeeById(long id);
//    Page<Employee> getEmployeesCreatedByAdmin(User admin, int pageNo, int pageSize, String sortField, String sortDir);
//    Page<Employee> getEmployeesCreatedByUser(User user, int pageNo, int pageSize, String sortField, String sortDir);
    void deleteEmployeeById(long id);
//    Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

    Page<Employee> getEmployeesCreatedBy(User user, int pageNo, int pageSize, String sortField, String sortDirection);
    Page<Employee> getAllEmployees(int pageNo, int pageSize, String sortField, String sortDirection);
    void changePassword(Employee employee, String newPassword);
}
