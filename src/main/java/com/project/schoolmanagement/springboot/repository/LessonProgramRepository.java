package com.project.schoolmanagement.springboot.repository;

import com.project.schoolmanagement.springboot.entity.concretes.LessonProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public interface LessonProgramRepository extends JpaRepository<LessonProgram, Long> {
    List<LessonProgram> findByTeachers_IdNotNull();

    List<LessonProgram> findByTeachers_IdNull();

    @Query("SELECT l FROM LessonProgram l INNER JOIN l.teachers teachers WHERE teachers.username = ?1")
    Set<LessonProgram> getLessonProgramByTeachersUsername(String username);
}
