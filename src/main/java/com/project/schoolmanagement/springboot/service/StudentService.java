package com.project.schoolmanagement.springboot.service;

import com.project.schoolmanagement.springboot.entity.concretes.AdvisoryTeacher;
import com.project.schoolmanagement.springboot.entity.concretes.LessonProgram;
import com.project.schoolmanagement.springboot.entity.concretes.Student;
import com.project.schoolmanagement.springboot.entity.enums.RoleType;
import com.project.schoolmanagement.springboot.exception.ResourceNotFoundException;
import com.project.schoolmanagement.springboot.payload.mappers.StudentDto;
import com.project.schoolmanagement.springboot.payload.reponse.ResponseMessage;
import com.project.schoolmanagement.springboot.payload.reponse.StudentResponse;
import com.project.schoolmanagement.springboot.payload.request.ChooseLessonProgramWithId;
import com.project.schoolmanagement.springboot.payload.request.StudentRequest;
import com.project.schoolmanagement.springboot.repository.StudentRepository;
import com.project.schoolmanagement.springboot.utility.CheckParameterUpdateMethod;
import com.project.schoolmanagement.springboot.utility.CheckSameLessonProgram;
import com.project.schoolmanagement.springboot.utility.Messages;
import com.project.schoolmanagement.springboot.utility.ServiceHelpers;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final AdvisoryTeacherService advisoryTeacherService;
    private final StudentRepository studentRepository;
    private final ServiceHelpers serviceHelpers;

    private final LessonProgramService lessonProgramService;
    private final StudentDto studentDto;
    private final UserRoleService userRoleService;

    private final CheckSameLessonProgram checkSameLessonProgram;
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

    public ResponseMessage deleteStudentById(Long id) {

        isStudentExist(id);

        studentRepository.deleteById(id);
        return ResponseMessage.builder()
                .message("Student deleted")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public List<StudentResponse> findStudentsByName(String studentName) {

    return studentRepository.getStudentByNameContaining(studentName)
            .stream()
            .map(studentDto::mapStudentToStudentResponse)
            .collect(Collectors.toList());
    }

    public Page<StudentResponse> search(int page, int size, String sort, String type) {

        Pageable pageable = serviceHelpers.getPageableWithProperties(page, size, sort, type);

        return studentRepository.findAll(pageable)
                .map(studentDto::mapStudentToStudentResponse);
    }

    public Student getStudentById(Long id) {

        return isStudentExist(id);
    }

    public List<StudentResponse> getAllAdvisoryUsername(String userName) {

        return studentRepository.getStudentByAdvisoryTeacher_Username(userName)
                .stream()
                .map(studentDto::mapStudentToStudentResponse)
                .collect(Collectors.toList());

    }

    public ResponseMessage<StudentResponse> chooseLesson(String userName,
                                                         ChooseLessonProgramWithId chooseLessonProgramWithId) {

        Student student = isStudentsExistByUsername(userName);
        Set<LessonProgram> lessonProgramSet = lessonProgramService
                .getLessonProgramById(chooseLessonProgramWithId.getLessonProgramId());
        Set<LessonProgram>studentCurrentLessonProgram = student.getLessonsProgramList();
        checkSameLessonProgram.checkLessonPrograms(lessonProgramSet,studentCurrentLessonProgram);
        studentCurrentLessonProgram.addAll(lessonProgramSet);
        //we are updating the lesson program of the student
        student.setLessonsProgramList(studentCurrentLessonProgram);

        Student savedStudent = studentRepository.save(student);

        return ResponseMessage.<StudentResponse>builder()
                .message("Lessons added to student")
                .object(studentDto.mapStudentToStudentResponse(savedStudent))
                .httpStatus(HttpStatus.OK)
                .build();
    }

    private Student isStudentsExistByUsername(String username) {
        Student student = studentRepository.findByUsernameEquals(username);

        if (student.getId()==null){
            throw new ResourceNotFoundException(Messages.NOT_FOUND_USER_MESSAGE);
        }
        return student;
    }
}
