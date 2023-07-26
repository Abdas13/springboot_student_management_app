package com.project.schoolmanagement.springboot.payload.mappers;

import com.project.schoolmanagement.springboot.entity.concretes.Teacher;
import com.project.schoolmanagement.springboot.payload.reponse.TeacherResponse;
import com.project.schoolmanagement.springboot.payload.request.TeacherRequest;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class TeacherDto {
    public Teacher mapTeacherRequestToTeacher(TeacherRequest teacherRequest) {

        return Teacher.builder()
                .name(teacherRequest.getName())
                .lastname(teacherRequest.getLastname())
                .ssn(teacherRequest.getSsn())
                .username(teacherRequest.getUsername())
                .birthday(teacherRequest.getBirthday())
                .birthPlace(teacherRequest.getBirthPlace())
                .password(teacherRequest.getPassword())
                .phoneNumber(teacherRequest.getPhoneNumber())
                .email(teacherRequest.getEmail())
                .isAdvisor(teacherRequest.isAdvisorTeacher())
                .gender(teacherRequest.getGender())
                .build();
    }

    public TeacherResponse mapTeacherToTeacherResponse(Teacher teacher){

        return TeacherResponse.builder()
                .userId(teacher.getId())
                .username(teacher.getUsername())
                .name(teacher.getName())
                .lastname(teacher.getLastname())
                .birthday(teacher.getBirthday())
                .birthPlace(teacher.getBirthPlace())
                .ssn(teacher.getSsn())
                .phoneNumber(teacher.getPhoneNumber())
                .gender(teacher.getGender())
                .email(teacher.getEmail())
                .build();
    }

    public Teacher mapTeacherRequestToUpdatedTeacher(TeacherRequest teacherRequest, Long id){

        Teacher teacher = mapTeacherRequestToTeacher(teacherRequest);
        teacher.setId(id);

        return teacher;
    }
}
