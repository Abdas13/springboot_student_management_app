package com.project.schoolmanagement.springboot.service;

import com.project.schoolmanagement.springboot.entity.concretes.EducationTerm;
import com.project.schoolmanagement.springboot.entity.concretes.Lesson;
import com.project.schoolmanagement.springboot.entity.concretes.LessonProgram;
import com.project.schoolmanagement.springboot.exception.BadRequestException;
import com.project.schoolmanagement.springboot.exception.ResourceNotFoundException;
import com.project.schoolmanagement.springboot.payload.mappers.LessonProgramDto;
import com.project.schoolmanagement.springboot.payload.reponse.LessonProgramResponse;
import com.project.schoolmanagement.springboot.payload.reponse.ResponseMessage;
import com.project.schoolmanagement.springboot.payload.request.LessonProgramRequest;
import com.project.schoolmanagement.springboot.repository.LessonProgramRepository;
import com.project.schoolmanagement.springboot.utility.Messages;
import com.project.schoolmanagement.springboot.utility.ServiceHelpers;
import com.project.schoolmanagement.springboot.utility.TimeControl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonProgramService {

    private final LessonProgramRepository lessonProgramRepository;

    private final LessonService lessonService;

    private final EducationTermService educationTermService;
    private final LessonProgramDto lessonProgramDto;

    private final ServiceHelpers serviceHelpers;
    public ResponseMessage<LessonProgramResponse> saveLessonProgram(LessonProgramRequest lessonProgramRequest) {

        Set<Lesson> lessons = lessonService.getLessonByLessonIdSet(lessonProgramRequest.getLessonIdList());

        EducationTerm educationTerm = educationTermService.getEducationTermById(lessonProgramRequest.getEducationTermId());

        if (lessons.size()==0){
            throw new ResourceNotFoundException(Messages.NOT_FOUND_LESSON_IN_LIST);
        }
//        else if (TimeControl.checkTime(lessonProgramRequest.getStartTime(),lessonProgramRequest.getStopTime())) {
//            throw new BadRequestException(Messages.TIME_NOT_VALID_MESSAGE);
//        }
        TimeControl.checkTimeWithException(lessonProgramRequest.getStartTime(), lessonProgramRequest.getStopTime());
        LessonProgram lessonProgram = lessonProgramDto.mapLessonProgramRequestToLessonProgram(lessonProgramRequest, lessons, educationTerm);

        LessonProgram savedLessonProgram = lessonProgramRepository.save(lessonProgram);


       return ResponseMessage.<LessonProgramResponse>builder()
               .message("Lesson Program is Created")
               .httpStatus(HttpStatus.CREATED)
               .object(lessonProgramDto.mapLessonProgramToLessonProgramResponse(savedLessonProgram))
               .build();
       /*
       public Set<Lesson> getLessonByLessonIdSet(Set<Long> lessons) {
       return lessonRepository.getLessonByLessonIdList(lessons);
        */
    }
    public List<LessonProgramResponse> getAllLessonProgramList() {

        return lessonProgramRepository
                .findAll()
                .stream()
                .map(lessonProgramDto::mapLessonProgramToLessonProgramResponse)
                .collect(Collectors.toList());
    }

    public LessonProgramResponse getLessonProgramById(Long id) {

        isLessonProgramExist(id);

        return lessonProgramDto.mapLessonProgramToLessonProgramResponse(lessonProgramRepository.findById(id).get());
    }

    public List<LessonProgramResponse> getAllLessonProgramUnassigned() { // teachers will be null

        return lessonProgramRepository.findByTeachers_IdNull()
                .stream()
                .map(lessonProgramDto::mapLessonProgramToLessonProgramResponse)
                .collect(Collectors.toList());
    }

    public List<LessonProgramResponse> getAllLessonProgramAssigned() {
        return lessonProgramRepository.findByTeachers_IdNotNull()
                .stream()
                .map(lessonProgramDto::mapLessonProgramToLessonProgramResponse)
                .collect(Collectors.toList());
    }

    public ResponseMessage deleteLessonProgramById(Long id) {
        isLessonProgramExist(id);

        lessonProgramRepository.deleteById(id);

        return ResponseMessage.builder()
                .message("Lesson program is deleted successfully")
                .httpStatus(HttpStatus.OK)
                .build();



    }

    private void isLessonProgramExist(Long id) {

        Optional<LessonProgram> lessonProgram = lessonProgramRepository.findById(id);

        if (lessonProgram.isEmpty()){
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_LESSON_PROGRAM_MESSAGE));
        }
    }

    public Page<LessonProgramResponse> getAllLessonProgramByPage(int page, int size, String sort, String type) {

        Pageable pageable = serviceHelpers.getPageableWithProperties(page, size, sort, type);

        return lessonProgramRepository.findAll(pageable).map(lessonProgramDto::mapLessonProgramToLessonProgramResponse);
    }

    // TODO add a validation for empty collection and send a meaningful response
    public Set<LessonProgramResponse> getLessonProgramByTeacher(String userName) {
        return lessonProgramRepository.getLessonProgramByTeachersUsername(userName)
                .stream()
                .map(lessonProgramDto::mapLessonProgramToLessonProgramResponse)
                .collect(Collectors.toSet());
    }

    public Set<LessonProgram> getLessonProgramById(Set<Long> lessonIdSet){

        Set<LessonProgram> lessonPrograms = lessonProgramRepository.getLessonProgramByLessonProgramIdList(lessonIdSet);

        if (lessonPrograms.isEmpty()){
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_LESSON_PROGRAM_MESSAGE));
        }
        return lessonPrograms;
    }
}
