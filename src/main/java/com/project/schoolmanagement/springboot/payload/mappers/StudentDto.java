package com.project.schoolmanagement.springboot.payload.mappers;

import com.project.schoolmanagement.springboot.entity.concretes.Student;
import com.project.schoolmanagement.springboot.payload.reponse.StudentResponse;
import com.project.schoolmanagement.springboot.payload.request.StudentRequest;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class StudentDto {

    public Student mapStudentRequestToStudent(StudentRequest studentRequest){

        return Student.builder()
                .fatherName(studentRequest.getFatherName())
                .motherName(studentRequest.getMotherName())
                .birthday(studentRequest.getBirthday())
                .birthPlace(studentRequest.getBirthPlace())
                .name(studentRequest.getName())
                .lastname(studentRequest.getLastname())
                .password(studentRequest.getPassword())
                .username(studentRequest.getUsername())
                .ssn(studentRequest.getSsn())
                .email(studentRequest.getEmail())
                .phoneNumber(studentRequest.getPhoneNumber())
                .gender(studentRequest.getGender())
                .build();
    }

    public StudentResponse mapStudentToStudentResponse(Student student){

        return StudentResponse.builder()
                .userId(student.getId())
                .username(student.getUsername())
                .name(student.getName())
                .lastname(student.getLastname())
                .birthday(student.getBirthday())
                .birthPlace(student.getBirthPlace())
                .phoneNumber(student.getPhoneNumber())
                .gender(student.getGender())
                .email(student.getEmail())
                .fatherName(student.getFatherName())
                .motherName(student.getMotherName())
                .studentNumber(student.getStudentNumber())
                .isActive(student.isActive())
                .build();
    }

    public Student mapStudentRequestToUpdatedStudent(StudentRequest studentRequest, Long studentId) {

        Student student = mapStudentRequestToStudent(studentRequest);
        student.setId(studentId);
        return student;
    }
}
