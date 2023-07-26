package com.project.schoolmanagement.springboot.controller;

import com.project.schoolmanagement.springboot.entity.abstracts.User;
import com.project.schoolmanagement.springboot.payload.reponse.LessonResponse;
import com.project.schoolmanagement.springboot.payload.reponse.ResponseMessage;
import com.project.schoolmanagement.springboot.payload.reponse.TeacherResponse;
import com.project.schoolmanagement.springboot.payload.request.TeacherRequest;
import com.project.schoolmanagement.springboot.service.TeacherService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public List<TeacherResponse> getAllTeachers(){

        return teacherService.getAllTeachers();
    }
    @GetMapping("/getTeacherByName")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public List<TeacherResponse> getTeacherByName(@RequestParam (name = "name") String teacherName){

        return teacherService.getTeacherByName(teacherName);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @DeleteMapping("/delete/{id}")
    public ResponseMessage deleteTeacherById(@PathVariable Long id){

        return teacherService.deleteTeacherById(id);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @GetMapping("getSavedTeacherById/{id}")
    public ResponseMessage<TeacherResponse> findTeacherById(@PathVariable Long id){
        return teacherService.getTeacherById(id);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public Page<TeacherResponse> search(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort") String sort,
            @RequestParam(value = "type") String type) {
        return teacherService.findTeacherByPage(page, size, sort, type);
    }
    @PutMapping("/update/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseMessage<TeacherResponse> updateTeacher(@RequestBody @Valid TeacherRequest teacherRequest,
                                                          @PathVariable Long userId){

        return teacherService.updateTeacher(teacherRequest, userId);
    }



}
