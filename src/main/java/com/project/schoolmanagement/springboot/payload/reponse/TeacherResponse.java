package com.project.schoolmanagement.springboot.payload.reponse;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.schoolmanagement.springboot.entity.concretes.LessonProgram;
import com.project.schoolmanagement.springboot.payload.reponse.abstracts.BaseUserResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeacherResponse extends BaseUserResponse {

    private Set<LessonProgram> lessonPrograms;
    private String email;
}
