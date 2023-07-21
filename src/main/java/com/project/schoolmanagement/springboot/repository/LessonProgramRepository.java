package com.project.schoolmanagement.springboot.repository;

import com.project.schoolmanagement.springboot.entity.concretes.LessonProgram;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonProgramRepository extends JpaRepository<LessonProgram, Long> {
}
