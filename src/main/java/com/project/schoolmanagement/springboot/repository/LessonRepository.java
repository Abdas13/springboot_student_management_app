package com.project.schoolmanagement.springboot.repository;

import com.project.schoolmanagement.springboot.entity.concretes.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;
import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    boolean existsLessonByLessonNameEqualsIgnoreCase(String lessonName);


    void findByLessonName(String lessonName);

    Optional<Lesson> getLessonByLessonName(String lessonName);
}
