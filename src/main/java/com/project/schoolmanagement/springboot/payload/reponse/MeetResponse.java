package com.project.schoolmanagement.springboot.payload.reponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.schoolmanagement.springboot.entity.concretes.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Builder(toBuilder = true)
public class MeetResponse {

    private Long id;
    private String description;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime stopTime;
    private Long advisorTeacherId;
    private String teacherName;
    private String teacherSsn;
    private String username;
    private List<Student> students;
}
