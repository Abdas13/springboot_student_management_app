package com.project.schoolmanagement.springboot.repository;

import com.project.schoolmanagement.springboot.entity.concretes.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByUsername(String username);

    boolean existsBySsn(String ssn);

    boolean existsByPhoneNumber(String phoneNumber);

    Student findByUsernameEquals(String username);

    boolean existsByEmail(String email);
}
