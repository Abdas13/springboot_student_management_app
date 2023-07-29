package com.project.schoolmanagement.springboot.controller;


import com.project.schoolmanagement.springboot.payload.reponse.ResponseMessage;
import com.project.schoolmanagement.springboot.payload.reponse.StudentInfoResponse;
import com.project.schoolmanagement.springboot.payload.request.StudentInfoRequest;
import com.project.schoolmanagement.springboot.service.StudentInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("studentInfo")
@RequiredArgsConstructor
public class StudentInfoController {

    private final StudentInfoService studentInfoService;

    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    @PostMapping("/save")
    public ResponseMessage<StudentInfoResponse> saveStudentInfo(HttpServletRequest httpServletRequest,
                                                                @RequestBody @Valid StudentInfoRequest studentInfoRequest){

        String username = (String) httpServletRequest.getAttribute("username");
        return studentInfoService.saveStudentInfo(username, studentInfoRequest);
    }




}