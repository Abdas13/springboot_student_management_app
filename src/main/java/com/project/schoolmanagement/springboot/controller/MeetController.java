package com.project.schoolmanagement.springboot.controller;

import com.project.schoolmanagement.springboot.payload.reponse.MeetResponse;
import com.project.schoolmanagement.springboot.payload.reponse.ResponseMessage;
import com.project.schoolmanagement.springboot.payload.request.MeetRequest;
import com.project.schoolmanagement.springboot.service.MeetService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("meet")
@RequiredArgsConstructor
public class MeetController {


    private final MeetService meetService;
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    public ResponseMessage<MeetResponse>saveMeet(HttpServletRequest httpServletRequest,
                                                 @RequestBody @Valid MeetRequest meetRequest){

        String username = (String) httpServletRequest.getAttribute("username");
        return meetService.saveMeet(username, meetRequest);
    }
}
