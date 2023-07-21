package com.project.schoolmanagement.springboot.payload.reponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.schoolmanagement.springboot.entity.concretes.LessonProgram;
import com.project.schoolmanagement.springboot.payload.reponse.abstracts.BaseUserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentResponse extends BaseUserResponse {

    private Set<LessonProgram> lessonProgramSet;
    private int studentNumber;
    private String motherName;
    private String fatherName;
    private String email;
    private boolean isActive;
}
