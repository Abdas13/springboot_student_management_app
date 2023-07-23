package com.project.schoolmanagement.springboot.service;

import com.project.schoolmanagement.springboot.entity.concretes.Lesson;
import com.project.schoolmanagement.springboot.exception.ConflictException;
import com.project.schoolmanagement.springboot.exception.ResourceNotFoundException;
import com.project.schoolmanagement.springboot.payload.mappers.LessonDto;
import com.project.schoolmanagement.springboot.payload.reponse.Error;
import com.project.schoolmanagement.springboot.payload.reponse.LessonResponse;
import com.project.schoolmanagement.springboot.payload.reponse.ResponseMessage;
import com.project.schoolmanagement.springboot.payload.request.LessonRequest;
import com.project.schoolmanagement.springboot.repository.LessonRepository;
import com.project.schoolmanagement.springboot.utility.Messages;
import com.project.schoolmanagement.springboot.utility.ServiceHelpers;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;

    private final ServiceHelpers serviceHelpers;

    private final LessonDto lessonDto;

    public ResponseMessage<LessonResponse> saveLesson(LessonRequest lessonRequest) {

        isLessonExistByLessonName(lessonRequest.getLessonName());

        Lesson savedLesson = lessonRepository.save(lessonDto.mapLessonRequestToLesson(lessonRequest));

        return ResponseMessage.<LessonResponse>builder()
                .message("Lesson Created Successfully")
                .httpStatus(HttpStatus.CREATED)
                .object(lessonDto.mapLessonToLessonResponse(savedLesson))
                .build();
    }

    private boolean isLessonExistByLessonName(String lessonName) {

       boolean lessonExist = lessonRepository.existsLessonByLessonNameEqualsIgnoreCase(lessonName);

       if (!lessonExist){
           return true;
       }else
           throw new ConflictException(String.format(Messages.ALREADY_REGISTER_LESSON_MESSAGE, lessonName));
    }
    private void isLessonExistById(Long id) {

        lessonRepository.findById(id).orElseThrow(()-> {

                    throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_LESSON_MESSAGE, id));
                });
    }

    public ResponseMessage<?> deleteLessonById(Long id) {
        isLessonExistById(id);

        lessonRepository.deleteById(id);

        return ResponseMessage.builder()
                .message("Lesson is deleted successfully.")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public ResponseMessage<LessonResponse> getLessonByLessonName(String lessonName) {

        return ResponseMessage.<LessonResponse> builder()
                .message("Lesson is successfully found")
                .object(lessonDto.mapLessonToLessonResponse(lessonRepository.getLessonByLessonName(lessonName).get()))
                .build();

    }

    public Page<LessonResponse> findLessonByPage(int page, int size, String sort, String type) {

        Pageable pageable = serviceHelpers.getPageableWithProperties(page, size, sort, type);

        return lessonRepository.findAll(pageable).map(lessonDto::mapLessonToLessonResponse);
    }

    // TODO add exception if get an empty collection
    public Set<Lesson> getLessonByLessonIdSet(Set<Long> lessons) {
       return lessonRepository.getLessonByLessonIdList(lessons);

    }
}
