package com.project.schoolmanagement.springboot.entity.concretes;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.schoolmanagement.springboot.entity.enums.Day;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LessonProgram implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Day day;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "US")
    private LocalTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "US")
    private LocalTime stopTime;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "lesson_program_lesson",
            joinColumns = @JoinColumn(name = "lessonprogram_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_id")
    )
    private Set<Lesson> lesson;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private EducationTerm educationTerm;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ManyToMany(mappedBy = "lessonProgramList", fetch = FetchType.EAGER)
    private Set<Teacher> teachers;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ManyToMany(mappedBy = "lessonProgramList", fetch = FetchType.EAGER)
    private Set<Student> students ;

    @PreRemove
    private void removeLessonProgramFromStudent(){
        teachers.forEach(teacher -> teacher.getLessonProgramList().remove(this));
        students.forEach(student -> student.getLessonProgramList().remove(this));
    }




}
