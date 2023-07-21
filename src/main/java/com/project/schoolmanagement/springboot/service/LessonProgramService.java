package com.project.schoolmanagement.springboot.service;

import com.project.schoolmanagement.springboot.entity.concretes.EducationTerm;
import com.project.schoolmanagement.springboot.entity.concretes.Lesson;
import com.project.schoolmanagement.springboot.payload.reponse.LessonProgramResponse;
import com.project.schoolmanagement.springboot.payload.reponse.ResponseMessage;
import com.project.schoolmanagement.springboot.payload.request.LessonProgramRequest;
import com.project.schoolmanagement.springboot.repository.LessonProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class LessonProgramService {

    private final LessonProgramRepository lessonProgramRepository;

    private final LessonService lessonService;

    private final EducationTermService educationTermService;
    public ResponseMessage<LessonProgramResponse> saveLessonProgram(LessonProgramRequest lessonProgramRequest) {

        Set<Lesson> lessons = lessonService.getLessonByLessonIdSet(lessonProgramRequest.getLessonIdList());

        EducationTerm educationTerm = educationTermService.getEducationTermById(lessonProgramRequest.getEducationTermId());


       return null;
    }
}
