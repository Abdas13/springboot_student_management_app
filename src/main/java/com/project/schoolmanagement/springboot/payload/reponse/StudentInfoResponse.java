package com.project.schoolmanagement.springboot.payload.reponse;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.schoolmanagement.springboot.entity.enums.Note;
import com.project.schoolmanagement.springboot.entity.enums.Term;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentInfoResponse {

    private Long id;
    private Double midTermExam;
    private Double finalExam;
    private Integer absentee;
    private String infoNote;
    private String lessonName;
    private int creditScore;
    private boolean isCompulsory;
    private Term educationTerm;
    private Double average;
    private Note note;
    private StudentResponse studentResponse;
}
