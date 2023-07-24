package com.project.schoolmanagement.springboot.controller;

import com.project.schoolmanagement.springboot.entity.abstracts.User;
import com.project.schoolmanagement.springboot.payload.reponse.ResponseMessage;
import com.project.schoolmanagement.springboot.payload.reponse.TeacherResponse;
import com.project.schoolmanagement.springboot.payload.request.abstracts.TeacherRequest;
import com.project.schoolmanagement.springboot.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("teachers")
@RequiredArgsConstructor
public class TeacherController extends User {

    private final TeacherService teacherService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseMessage<TeacherResponse> saveTeacher(@RequestBody @Valid TeacherRequest teacherRequest){

        return teacherService.saveTeacher(teacherRequest);
    }
}
