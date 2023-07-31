package com.project.schoolmanagement.springboot.controller;

import com.project.schoolmanagement.springboot.entity.concretes.AdvisoryTeacher;
import com.project.schoolmanagement.springboot.payload.mappers.AdvisoryTeacherDto;
import com.project.schoolmanagement.springboot.payload.reponse.AdvisorTeacherResponse;
import com.project.schoolmanagement.springboot.payload.reponse.ResponseMessage;
import com.project.schoolmanagement.springboot.service.AdvisoryTeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("advisorTeacher")
@RequiredArgsConstructor
public class AdvisoryTeacherController {


    private final AdvisoryTeacherService advisoryTeacherService;
    private final AdvisoryTeacherDto advisoryTeacherDto;

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @GetMapping("/getAll")
    public List<AdvisorTeacherResponse> getAllAdvisorTeachers(){

        return advisoryTeacherService.getAll();
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @GetMapping("/search")
    public Page<AdvisorTeacherResponse> search(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort") String sort,
            @RequestParam(value = "type") String type
    ){
        return advisoryTeacherService.search(page,size,sort,type);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @DeleteMapping("/delete/{id}")
    public ResponseMessage deleteAdvisorTeacher(@PathVariable Long id){
        return advisoryTeacherService.deleteAdvisorTeacherById(id);
    }
    //will be checked (what is "username" for advisorTeacher)
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @GetMapping("/getByUsername")
    public ResponseMessage<AdvisorTeacherResponse> getByUsername(@RequestParam String username){
        AdvisoryTeacher advisoryTeacher = advisoryTeacherService.getAdvisorTeacherByUsername(username);
        return ResponseMessage.<AdvisorTeacherResponse>builder()
                .message("Done")
                .httpStatus(HttpStatus.OK)
                .object(advisoryTeacherDto.mapAdvisorTeacherToAdvisorTeacherResponse(advisoryTeacher))
                .build();
    }


}
