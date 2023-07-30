package com.project.schoolmanagement.springboot.controller;


import com.project.schoolmanagement.springboot.payload.reponse.ResponseMessage;
import com.project.schoolmanagement.springboot.payload.reponse.StudentInfoResponse;
import com.project.schoolmanagement.springboot.payload.request.StudentInfoRequest;
import com.project.schoolmanagement.springboot.payload.request.UpdateStudentInfoRequest;
import com.project.schoolmanagement.springboot.service.StudentInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

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
    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    @DeleteMapping("/delete/{studentInfoId}")
    public ResponseMessage delete(@PathVariable Long studentInfoId){

        return studentInfoService.deleteStudentInfo(studentInfoId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    @GetMapping("/search")
    public Page<StudentInfoResponse> search(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort") String sort,
            @RequestParam(value = "type") String type
    ){
        return studentInfoService.search(page, size, sort, type);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    @PutMapping("/update/{studentInfoId}")
    public ResponseMessage<StudentInfoResponse> update(@RequestBody @Valid UpdateStudentInfoRequest studentInfoRequest,
                                          @PathVariable Long studentInfoId){

        return studentInfoService.update(studentInfoRequest, studentInfoId);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    @GetMapping("/getAllForTeacher")
    public ResponseEntity<Page<StudentInfoResponse>> getAllForTeacher(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size){
        return new ResponseEntity<>(studentInfoService.getAllForTeacher(httpServletRequest, page, size), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    @GetMapping("/getAllForStudent")
    public ResponseEntity<Page<StudentInfoResponse>> getAllForStudent(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size){
        return new ResponseEntity<>(studentInfoService.getAllForStudent(httpServletRequest, page, size), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    @GetMapping("/getByStudentId/{studentId}")
    public ResponseEntity<List<StudentInfoResponse>>getStudentInfoByStudentId(@PathVariable Long studentId){
        List<StudentInfoResponse> studentInfoResponse = studentInfoService.getStudentInfoByStudentId(studentId);

        return ResponseEntity.ok(studentInfoResponse);
    }





}
