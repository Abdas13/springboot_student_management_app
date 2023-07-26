package com.project.schoolmanagement.springboot.repository;

import com.project.schoolmanagement.springboot.entity.concretes.AdvisoryTeacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdvisoryTeacherRepository extends JpaRepository<AdvisoryTeacher, Long> {


    Optional<AdvisoryTeacher> getAdvisoryTeacherByTeacher_Id(Long advisoryReacherId);


    Optional<AdvisoryTeacher>findByTeacher_UsernameEquals(String username);
}
