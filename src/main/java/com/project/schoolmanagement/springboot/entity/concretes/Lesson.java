package com.project.schoolmanagement.springboot.entity.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lessonId;

    private String lessonName;

    private Integer creditScore;

    private Boolean isCompulsory;
    @JsonIgnore
    @ManyToMany(mappedBy = "lessons")
    private Set<LessonProgram> lessonPrograms;








}
