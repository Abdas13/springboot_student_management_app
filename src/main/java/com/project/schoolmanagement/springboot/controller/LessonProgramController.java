package com.project.schoolmanagement.springboot.controller;

import com.project.schoolmanagement.springboot.payload.reponse.LessonProgramResponse;
import com.project.schoolmanagement.springboot.payload.reponse.ResponseMessage;
import com.project.schoolmanagement.springboot.payload.request.LessonProgramRequest;
import com.project.schoolmanagement.springboot.service.LessonProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("lessonPrograms")
@RequiredArgsConstructor
public class LessonProgramController {

    private final LessonProgramService lessonProgramService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseMessage<LessonProgramResponse> saveLessonProgram( @RequestBody @Valid LessonProgramRequest lessonProgramRequest){

        return lessonProgramService.saveLessonProgram(lessonProgramRequest);
    }
    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER', 'TEACHER', 'STUDENT')")
    public List<LessonProgramResponse> getAllLessonProgramByList(){

        return lessonProgramService.getAllLessonProgramList();
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER', 'TEACHER', 'STUDENT')")
    public LessonProgramResponse getLessonProgramById(@PathVariable Long id){

        return lessonProgramService.getLessonProgramById(id);
    }
    @GetMapping("/getAllUnassigned")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER', 'TEACHER', 'STUDENT')")
    public List<LessonProgramResponse> getAllUnassigned() {

        return lessonProgramService.getAllLessonProgramUnassigned();
    }
    @GetMapping("/getAllAssigned")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER', 'TEACHER', 'STUDENT')")
    public List<LessonProgramResponse> getAllAssigned() {

        return lessonProgramService.getAllLessonProgramAssigned();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseMessage deleteLessonProgramById(@PathVariable Long id){
        return lessonProgramService.deleteLessonProgramById(id);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public Page<LessonProgramResponse> search(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort") String sort,
            @RequestParam(value = "type") String type) {

        return lessonProgramService.getAllLessonProgramByPage(page,size,sort,type);
    }
    @PreAuthorize("hasAnyAuthority('TEACHER','ADMIN','MANAGER','ASSISTANT_MANAGER')")
    @GetMapping("/getAllLessonProgramByTeacher")
    public Set<LessonProgramResponse> getAllLessonProgramByTeacherUserName(HttpServletRequest httpServletRequest){
        String userName = httpServletRequest.getHeader("username");
        return lessonProgramService.getLessonProgramByTeacher(userName);
    }
}
