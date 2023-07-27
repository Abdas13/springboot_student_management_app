package com.project.schoolmanagement.springboot.service;

import com.project.schoolmanagement.springboot.entity.concretes.LessonProgram;
import com.project.schoolmanagement.springboot.entity.concretes.Teacher;
import com.project.schoolmanagement.springboot.entity.enums.RoleType;
import com.project.schoolmanagement.springboot.exception.ResourceNotFoundException;
import com.project.schoolmanagement.springboot.payload.mappers.TeacherDto;
import com.project.schoolmanagement.springboot.payload.reponse.ResponseMessage;
import com.project.schoolmanagement.springboot.payload.reponse.TeacherResponse;
import com.project.schoolmanagement.springboot.payload.request.ChooseLessonTeacherRequest;
import com.project.schoolmanagement.springboot.payload.request.TeacherRequest;
import com.project.schoolmanagement.springboot.repository.TeacherRepository;
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
public class TeacherService {

    private final LessonProgramService lessonProgramService;
    private final ServiceHelpers serviceHelpers;
    private final TeacherDto teacherDto;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;

    private final TeacherRepository teacherRepository;
    private final AdvisoryTeacherService advisoryTeacherService;

    private final CheckSameLessonProgram checkSameLessonProgram;

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

    public List<TeacherResponse> getAllTeachers() {

        return teacherRepository.findAll()
                .stream()
                .map(teacherDto::mapTeacherToTeacherResponse)
                .collect(Collectors.toList());
    }

    public List<TeacherResponse> getTeacherByName(String teacherName) {

        return teacherRepository.getTeachersByNameContaining(teacherName)
                .stream()
                .map(teacherDto::mapTeacherToTeacherResponse)
                .toList();
    }

    public ResponseMessage deleteTeacherById(Long id) {

        isTeacherExist(id);

        teacherRepository.deleteById(id);

        return ResponseMessage.builder()
                .message("teacher deleted successfully")
                .httpStatus(HttpStatus.OK)
                .build();

    }
    private Teacher isTeacherExist(Long id) {

        return teacherRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(Messages.NOT_FOUND_USER_MESSAGE));
    }
    public ResponseMessage<TeacherResponse> getTeacherById(Long id){

        return ResponseMessage.<TeacherResponse>builder()
                .object(teacherDto.mapTeacherToTeacherResponse(isTeacherExist(id)))
                .message("Teacher found")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public Page<TeacherResponse> findTeacherByPage(int page, int size, String sort, String type) {

        Pageable pageable = serviceHelpers.getPageableWithProperties(page, size, sort, type);

        return teacherRepository.findAll(pageable).map(teacherDto::mapTeacherToTeacherResponse);

    }

    public ResponseMessage<TeacherResponse> updateTeacher(TeacherRequest teacherRequest, Long userId) {

        Teacher teacher = isTeacherExist(userId);

        Set<LessonProgram> lessonPrograms = lessonProgramService.getLessonProgramById(teacherRequest.getLessonsIdList());

        if (!CheckParameterUpdateMethod.checkUniquePropertiesForTeacher(teacher, teacherRequest)){
            serviceHelpers.checkDuplicate(teacherRequest.getUsername(),
                    teacherRequest.getSsn(),
                    teacherRequest.getPhoneNumber(),
                    teacherRequest.getEmail());
        }
        Teacher updatedTeacher = teacherDto.mapTeacherRequestToUpdatedTeacher(teacherRequest, userId);
        updatedTeacher.setLessonsProgramList(lessonPrograms);

        Teacher savedTeacher = teacherRepository.save(updatedTeacher);

        advisoryTeacherService.updateAdvisoryTeacher(teacherRequest.isAdvisorTeacher(),savedTeacher);

        return ResponseMessage.<TeacherResponse> builder()
                .object(teacherDto.mapTeacherToTeacherResponse(savedTeacher))
                .message("Teacher successfully updated")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public ResponseMessage<TeacherResponse> chooseLesson(ChooseLessonTeacherRequest chooseLessonTeacherRequest) {

        Teacher teacher = isTeacherExist(chooseLessonTeacherRequest.getTeacherId());

        Set<LessonProgram> lessonPrograms = lessonProgramService.getLessonProgramById(chooseLessonTeacherRequest.getLessonProgramId());

        Set<LessonProgram> teachersLessonProgram = teacher.getLessonsProgramList();

        checkSameLessonProgram.checkLessonPrograms(teachersLessonProgram, lessonPrograms);
        lessonPrograms.addAll(lessonPrograms);
        teacher.setLessonsProgramList(teachersLessonProgram);
        Teacher updatedTeacher = teacherRepository.save(teacher);

        return ResponseMessage.<TeacherResponse>builder()
                .message("Lesson Program added to the teacher")
                .httpStatus(HttpStatus.CREATED)
                .object(teacherDto.mapTeacherToTeacherResponse(updatedTeacher))
                .build();
    }
}
