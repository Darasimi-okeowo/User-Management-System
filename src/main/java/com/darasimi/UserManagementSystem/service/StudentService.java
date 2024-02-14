package com.darasimi.UserManagementSystem.service;

import com.darasimi.UserManagementSystem.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {
    List<Student> getAllStudents();

    Student saveStudent(Student student);

    Student getStudentById(long id);

    Student updateStudent(Student student);

    void deleteStudentById(long id);
}
