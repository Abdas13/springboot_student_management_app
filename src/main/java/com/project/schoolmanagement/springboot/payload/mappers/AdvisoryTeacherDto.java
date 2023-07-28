package com.project.schoolmanagement.springboot.payload.mappers;


import com.project.schoolmanagement.springboot.entity.concretes.AdvisoryTeacher;
import com.project.schoolmanagement.springboot.entity.concretes.Teacher;
import com.project.schoolmanagement.springboot.payload.reponse.AdvisorTeacherResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class AdvisoryTeacherDto {

    public AdvisoryTeacher mapTeacherToAdvisoryTeacher(Teacher teacher){
        return AdvisoryTeacher.builder()
                .teacher(teacher)
                .build();
    }

    public AdvisorTeacherResponse mapAdvisorTeacherToAdvisorTeacherResponse(AdvisoryTeacher advisoryTeacher){

        return AdvisorTeacherResponse.builder()
                .advisorTeacherId(advisoryTeacher.getId())
                .teacherName(advisoryTeacher.getTeacher().getName())
                .teacherLastname(advisoryTeacher.getTeacher().getLastname())
                .teacherSSN(advisoryTeacher.getTeacher().getSsn())
                .build();
    }
}
