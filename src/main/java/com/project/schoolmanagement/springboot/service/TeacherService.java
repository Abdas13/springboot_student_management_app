package com.project.schoolmanagement.springboot.service;

import com.project.schoolmanagement.springboot.entity.concretes.AdvisoryTeacher;
import com.project.schoolmanagement.springboot.entity.concretes.LessonProgram;
import com.project.schoolmanagement.springboot.entity.concretes.Teacher;
import com.project.schoolmanagement.springboot.entity.enums.RoleType;
import com.project.schoolmanagement.springboot.payload.mappers.TeacherDto;
import com.project.schoolmanagement.springboot.payload.reponse.ResponseMessage;
import com.project.schoolmanagement.springboot.payload.reponse.TeacherResponse;
import com.project.schoolmanagement.springboot.payload.request.abstracts.TeacherRequest;
import com.project.schoolmanagement.springboot.repository.TeacherRepository;
import com.project.schoolmanagement.springboot.utility.ServiceHelpers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final LessonProgramService lessonProgramService;
    private final ServiceHelpers serviceHelpers;
    private final TeacherDto teacherDto;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;

    private final TeacherRepository teacherRepository;
    private final AdvisoryTeacherService advisoryTeacherService;

    public ResponseMessage<TeacherResponse> saveTeacher(TeacherRequest teacherRequest) {

        Set<LessonProgram> lessonProgramSet = lessonProgramService.getLessonProgramById(teacherRequest.getLessonsIdList());

        serviceHelpers.checkDuplicate(teacherRequest.getUsername(),
                                      teacherRequest.getSsn(),
                                      teacherRequest.getPhoneNumber(),
                                      teacherRequest.getEmail());

        Teacher teacher = teacherDto.mapTeacherRequestToTeacher(teacherRequest);

        teacher.setUserRole(userRoleService.getUserRole(RoleType.TEACHER));
        teacher.setLessonsProgramList(lessonProgramSet);
        teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));

        Teacher savedTeacher = teacherRepository.save(teacher);
        if (teacherRequest.isAdvisorTeacher()){
            advisoryTeacherService.saveAdvisoryTeacher(teacher);
        }
        return ResponseMessage.<TeacherResponse>builder()
                .message("Teacher saved successfully")
                .httpStatus(HttpStatus.CREATED)
                .object(teacherDto.mapTeacherToTeacherResponse(savedTeacher))
                .build();

    }
}
