package com.darasimi.UserManagementSystem.repository;

import com.darasimi.UserManagementSystem.entity.Employee;
import com.darasimi.UserManagementSystem.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findByCreatedBy(User user, Pageable pageable);
    Employee findByEmail(String email);
//    List<Employee> findEmployeesCreatedByAdmin(User user, Pageable pageable);
//    List<Employee> findEmployeesCreatedByUser(User user, Pageable pageable);

}
