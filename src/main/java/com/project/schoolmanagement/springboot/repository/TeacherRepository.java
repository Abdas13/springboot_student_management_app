package com.project.schoolmanagement.springboot.repository;

import com.project.schoolmanagement.springboot.entity.concretes.Teacher;
import com.project.schoolmanagement.springboot.payload.reponse.TeacherResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    boolean existsByUsername(String username);

    boolean existsBySsn(String ssn);

    boolean existsByPhoneNumber(String phoneNumber);

    Teacher findByUsernameEquals(String username);

    boolean existsByEmail(String email);

    List<Teacher> getTeachersByNameContaining(String teacherName);

    Teacher getTeachersByUsername(String username);
}
