package com.project.schoolmanagement.springboot.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class StudentInfoRequest {

    @NotNull(message = "Please select education term")
    private Long educationTermId;

    @DecimalMax("100.0")
    @DecimalMin("0.0")
    @NotNull(message = "Please enter midterm exam")
    private Double midTermExam;

    @DecimalMax("100.0")
    @DecimalMin("0.0")
    @NotNull(message = "Please enter final exam")
    private Double finalExam;

    @NotNull(message = "Please enter absentee")
    private Integer absentee;

    @NotNull(message = "Please enter info")
    @Size(max = 200, min = 10, message = "info should be at least 10 characters." )
    @Pattern(regexp = "\\A(?!\\s*\\Z).+" ,message="Info must consist of the characters .")
    private String infoNote;

    @NotNull(message = "Please select lesson")
    private Long lessonId;

    @NotNull(message = "Please select student")
    private Long studentId;

}
