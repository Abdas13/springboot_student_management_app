package com.project.schoolmanagement.springboot.service;

import com.project.schoolmanagement.springboot.entity.concretes.AdvisoryTeacher;
import com.project.schoolmanagement.springboot.entity.concretes.Student;
import com.project.schoolmanagement.springboot.entity.enums.RoleType;
import com.project.schoolmanagement.springboot.exception.ResourceNotFoundException;
import com.project.schoolmanagement.springboot.payload.mappers.StudentDto;
import com.project.schoolmanagement.springboot.payload.reponse.ResponseMessage;
import com.project.schoolmanagement.springboot.payload.reponse.StudentResponse;
import com.project.schoolmanagement.springboot.payload.request.StudentRequest;
import com.project.schoolmanagement.springboot.repository.StudentRepository;
import com.project.schoolmanagement.springboot.utility.CheckParameterUpdateMethod;
import com.project.schoolmanagement.springboot.utility.Messages;
import com.project.schoolmanagement.springboot.utility.ServiceHelpers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final AdvisoryTeacherService advisoryTeacherService;
    private final StudentRepository studentRepository;
    private final ServiceHelpers serviceHelpers;

    private final StudentDto studentDto;
    private final UserRoleService userRoleService;

    private final PasswordEncoder passwordEncoder;

    public ResponseMessage<StudentResponse> saveStudent(StudentRequest studentRequest) {

        AdvisoryTeacher advisoryTeacher = advisoryTeacherService.getAdvisoryTeacherById(studentRequest.getAdvisorTeacherId());
        //we need to check duplication
        //correct order since we have varargs
        serviceHelpers.checkDuplicate(studentRequest.getUsername(),
                studentRequest.getSsn(),
                studentRequest.getPhoneNumber(),
                studentRequest.getEmail());

        Student student = studentDto.mapStudentRequestToStudent(studentRequest);
        student.setAdvisoryTeacher(advisoryTeacher);
        student.setPassword(passwordEncoder.encode(studentRequest.getPassword()));
        student.setUserRole(userRoleService.getUserRole(RoleType.STUDENT));
        student.setActive(true);
        student.setStudentNumber(getLastNumber());

        studentRepository.save(student);
        return ResponseMessage.<StudentResponse>builder()
                .message("Student saved")
                .httpStatus(HttpStatus.CREATED)
                .object(studentDto.mapStudentToStudentResponse(student))
                .build();
    }

    public ResponseMessage changeStatus(Long studentId, boolean status) {

        Student student = isStudentExist(studentId);
        student.setActive(status);
        studentRepository.save(student);

        return ResponseMessage.builder()
                .message("Student is " + (status ? "active" : "passive"))
                .httpStatus(HttpStatus.OK)
                .build();
    }
    private Student isStudentExist(Long studentId) {

        return studentRepository.findById(studentId).orElseThrow(
                ()-> new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE,studentId)));
    }
    private int getLastNumber() {

        if (!studentRepository.findStudent()){
            // first student
            return 1001;
        }
        return studentRepository.getMaxStudentNumber()+1;
    }

    public List<StudentResponse> getAllStudents() {

        return studentRepository.findAll()
                .stream()
                .map(studentDto::mapStudentToStudentResponse)
                .collect(Collectors.toList());
    }

    public ResponseMessage<StudentResponse> updateStudent(Long studentId, StudentRequest studentRequest) {

        Student student = isStudentExist(studentId);

        AdvisoryTeacher advisoryTeacher = advisoryTeacherService.getAdvisoryTeacherById(studentRequest.getAdvisorTeacherId());

        if (!CheckParameterUpdateMethod.checkUniquePropertiesForStudent(student, studentRequest)){
            serviceHelpers.checkDuplicate(studentRequest.getUsername(),
                    studentRequest.getSsn(),
                    studentRequest.getPhoneNumber(),
                    studentRequest.getEmail());
        }
        Student studentForUpdate = studentDto.mapStudentRequestToUpdatedStudent(studentRequest,studentId);
        studentForUpdate.setPassword(passwordEncoder.encode(studentRequest.getPassword()));
        studentForUpdate.setAdvisoryTeacher(advisoryTeacher);
        studentForUpdate.setStudentNumber(student.getStudentNumber());
        studentForUpdate.setUserRole(userRoleService.getUserRole(RoleType.STUDENT));
        studentForUpdate.setActive(true);

        Student savedStudent = studentRepository.save(studentForUpdate);

        return ResponseMessage.<StudentResponse>builder()
                .message("Student updated successfully")
                .object(studentDto.mapStudentToStudentResponse(savedStudent))
                .httpStatus(HttpStatus.OK)
                .build();









    }
}
