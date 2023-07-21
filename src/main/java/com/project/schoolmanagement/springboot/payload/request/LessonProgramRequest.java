package com.project.schoolmanagement.springboot.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.schoolmanagement.springboot.entity.enums.Day;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LessonProgramRequest {

    @NotNull(message="Please enter the day")
    private Day day;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "US")
    @NotNull(message="Please enter start time")
    private LocalTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "US")
    @NotNull(message="Please enter stop time")
    private LocalTime stopTime;

    @NotNull(message="Please select the lesson")
    @Size(min=1, message ="Lesson must not be empty")
    private Set<Long> lessonIdList;

    @NotNull(message="Please enter education term")
    private Long educationTermId;

}
