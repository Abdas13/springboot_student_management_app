package com.project.schoolmanagement.springboot.controller;

import com.project.schoolmanagement.springboot.payload.reponse.MeetResponse;
import com.project.schoolmanagement.springboot.payload.reponse.ResponseMessage;
import com.project.schoolmanagement.springboot.payload.reponse.StudentInfoResponse;
import com.project.schoolmanagement.springboot.payload.request.MeetRequest;
import com.project.schoolmanagement.springboot.payload.request.UpdateMeetRequest;
import com.project.schoolmanagement.springboot.service.MeetService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("meet")
@RequiredArgsConstructor
public class MeetController {


    private final MeetService meetService;
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")  // only Teacher
    public ResponseMessage<MeetResponse>saveMeet(HttpServletRequest httpServletRequest,
                                                 @RequestBody @Valid MeetRequest meetRequest){

        String username = (String) httpServletRequest.getAttribute("username");
        return meetService.saveMeet(username, meetRequest);
    }
    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")  // only Admin
    public List<MeetResponse> getAll(){
        return meetService.getAll();
    }
    @GetMapping("/getMeetById/{meetId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    public ResponseMessage<MeetResponse> getMeetById(@PathVariable Long meetId){

        meetService.getMeetById(meetId);

        return  null;
    }
    @GetMapping("/delete/{meetId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    public ResponseMessage delete(@PathVariable Long meetId){
        return meetService.delete(meetId);
    }
    @PutMapping("/update/{meetId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    public ResponseMessage<MeetResponse> updateMeet(@RequestBody @Valid UpdateMeetRequest updateMeetRequest,
                                                    @PathVariable Long meetId){
        return meetService.updateMeet(updateMeetRequest, meetId);
    }
    @GetMapping("/getAllMeetByAdvisorTeacherAsList")
    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')") // only Teacher
    public ResponseEntity<List<MeetResponse>> getAllMeetByTeacher(HttpServletRequest httpServletRequest){
        return meetService.getAllMeetByTeacher(httpServletRequest);
    }

    @GetMapping("/getAllMeetByStudent")
    @PreAuthorize("hasAnyAuthority('ADMIN','STUDENT')") // only Student
    public List<MeetResponse> getAllMeetByStudent(HttpServletRequest httpServletRequest){
        return meetService.getAllMeetByStudent(httpServletRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/search")
    public Page<MeetResponse> search(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size){
        return meetService.search(page, size);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'TEACHER')")
    @GetMapping("/getAllMeetByAdvisorAsPage")
    public ResponseEntity<Page<MeetResponse>> getAllMeetByTeacher(
                                                            HttpServletRequest httpServletRequest,
                                                            @RequestParam(value = "page") int page,
                                                            @RequestParam(value = "size") int size){
        return meetService.getAllMeetByTeacher(httpServletRequest, page, size);
    }









}
