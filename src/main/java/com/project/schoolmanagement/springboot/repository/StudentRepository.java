package com.project.schoolmanagement.springboot.repository;

import com.project.schoolmanagement.springboot.entity.concretes.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByUsername(String username);

    boolean existsBySsn(String ssn);

    boolean existsByPhoneNumber(String phoneNumber);

    Student findByUsernameEquals(String username);

    boolean existsByEmail(String email);

    @Query(value = "SELECT (count (s)>0) FROM Student s")
    boolean findStudent();
    @Query(value = "SELECT MAX (s.studentNumber) FROM Student s")
    int getMaxStudentNumber();
}
