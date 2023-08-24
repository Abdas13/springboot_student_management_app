package com.project.schoolmanagement.springboot.service;

import com.project.schoolmanagement.springboot.payload.mappers.TeacherDto;
import com.project.schoolmanagement.springboot.repository.TeacherRepository;
import com.project.schoolmanagement.springboot.utility.CheckSameLessonProgram;
import com.project.schoolmanagement.springboot.utility.ServiceHelpers;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class TeacherServiceTest {

    @InjectMocks
    private TeacherService teacherService;

    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private final LessonProgramService lessonProgramService;
    @Mock
    private final ServiceHelpers serviceHelpers;
    @Mock
    private final TeacherDto teacherDto;
    @Mock
    private final UserRoleService userRoleService;
    @Mock
    private final PasswordEncoder passwordEncoder;
    @Mock
    private final AdvisoryTeacherService advisoryTeacherService;
    @Mock
    private final CheckSameLessonProgram checkSameLessonProgram;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveTeacher() {
    }

    @Test
    void getAllTeachers() {
    }
}