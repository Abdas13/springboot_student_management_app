package com.project.schoolmanagement.springboot.controller;

import com.project.schoolmanagement.springboot.payload.reponse.LessonProgramResponse;
import com.project.schoolmanagement.springboot.payload.reponse.ResponseMessage;
import com.project.schoolmanagement.springboot.payload.request.LessonProgramRequest;
import com.project.schoolmanagement.springboot.service.LessonProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("LessonPrograms")
@RequiredArgsConstructor
public class LessonProgramController {

    private final LessonProgramService lessonProgramService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseMessage<LessonProgramResponse> saveLessonProgram(@RequestBody @Valid LessonProgramRequest lessonProgramRequest){

        return lessonProgramService.saveLessonProgram(lessonProgramRequest);
    }
}
