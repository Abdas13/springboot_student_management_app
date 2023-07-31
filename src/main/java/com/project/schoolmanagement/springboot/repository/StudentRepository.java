package com.project.schoolmanagement.springboot.repository;

import com.project.schoolmanagement.springboot.entity.concretes.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

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

    @Query(value = "SELECT s FROM Student s WHERE s.advisoryTeacher.teacher.username =:username")
    List<Student> getStudentByAdvisoryTeacher_Username(String username);

    @Modifying
    @Query("DELETE FROM Student s WHERE s.id = :id")
    void deleteById(@Param("id") Long id);

    List<Student>getStudentByNameContaining(String studentName);

    boolean existsByIdEquals(Long id);
}
