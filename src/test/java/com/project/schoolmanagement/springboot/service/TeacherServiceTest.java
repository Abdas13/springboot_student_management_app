package com.project.schoolmanagement.springboot.service;

import com.project.schoolmanagement.springboot.entity.concretes.Teacher;
import com.project.schoolmanagement.springboot.payload.mappers.TeacherDto;
import com.project.schoolmanagement.springboot.payload.reponse.TeacherResponse;
import com.project.schoolmanagement.springboot.repository.TeacherRepository;
import com.project.schoolmanagement.springboot.utility.CheckSameLessonProgram;
import com.project.schoolmanagement.springboot.utility.ServiceHelpers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;


//@ExtendWith(MockitoExtension.class)  // line 55 is the same
class TeacherServiceTest {

    @InjectMocks
    private TeacherService mockTeacherService;

    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private  LessonProgramService lessonProgramService;
    @Mock
    private  ServiceHelpers serviceHelpers;
    @Mock
    private  TeacherDto teacherDto;
    @Mock
    private  UserRoleService userRoleService;
    @Mock
    private  PasswordEncoder passwordEncoder;
    @Mock
    private  AdvisoryTeacherService advisoryTeacherService;
    @Mock
    private  CheckSameLessonProgram checkSameLessonProgram;

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
    void getTeacherById(Long id) {

    }

    @Test
    void getAllTeachers_successTest(){
        mockTeacherService.getAllTeachers();
        Mockito.verify(teacherRepository,times(0)).findAll();

    }
    @Test
    void getAllTeachers_successListTest() {

        List<Teacher> teacherList = new ArrayList<>();
        teacherList.add(new Teacher());
        teacherList.add(new Teacher());
        when(teacherRepository.findAll()).thenReturn(teacherList);
        List<TeacherResponse> teacherResponseList = mockTeacherService.getAllTeachers();
        Assertions.assertEquals(teacherList.size(),teacherResponseList.size(), "FAIL");



    }
}