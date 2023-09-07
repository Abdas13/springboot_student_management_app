package com.project.schoolmanagement.springboot.repository;

import com.project.schoolmanagement.springboot.entity.concretes.Teacher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TeacherRepositoryTest {

    @Autowired
    TeacherRepository teacherRepositoryTest;

    @BeforeEach
    void setUp() {
        Teacher mocKTeacher = Teacher.builder()
                        .email("abs@gg.com")
                                .build();
        teacherRepositoryTest.save(mocKTeacher);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void existsByUsername() {
    }

    @Test
    void existsBySsn() {
    }

    @Test
    void existsByPhoneNumber() {
    }

    @Test
    void findByUsernameEquals() {
    }

    @Test
    void existsByEmail() {

        boolean mockResponse = teacherRepositoryTest.existsByEmail("abs@gg.com");

        Assertions.assertTrue(mockResponse);

        boolean mockResponse2 = teacherRepositoryTest.existsByEmail("abc@gg.com");

        Assertions.assertFalse(mockResponse2);

    }

    @Test
    void getTeachersByNameContaining() {
    }

    @Test
    void getTeachersByUsername() {
    }
}