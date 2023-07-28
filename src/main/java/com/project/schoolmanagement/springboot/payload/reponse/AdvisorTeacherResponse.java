package com.project.schoolmanagement.springboot.payload.reponse;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AdvisorTeacherResponse {

    private Long advisorTeacherId;
    private String teacherName;
    private String teacherSSN;
    private String teacherLastname;


}
