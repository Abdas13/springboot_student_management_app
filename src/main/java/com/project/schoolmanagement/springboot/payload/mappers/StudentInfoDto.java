package com.project.schoolmanagement.springboot.payload.mappers;

import com.project.schoolmanagement.springboot.entity.concretes.EducationTerm;
import com.project.schoolmanagement.springboot.entity.concretes.Lesson;
import com.project.schoolmanagement.springboot.entity.concretes.StudentInfo;
import com.project.schoolmanagement.springboot.entity.enums.Note;
import com.project.schoolmanagement.springboot.payload.reponse.StudentInfoResponse;
import com.project.schoolmanagement.springboot.payload.request.StudentInfoRequest;
import com.project.schoolmanagement.springboot.payload.request.UpdateStudentInfoRequest;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class StudentInfoDto {

    private final StudentDto studentDto;

    public StudentInfo mapStudentInfoRequestToStudentInfo(StudentInfoRequest studentInfoRequest,
                                                          Note note,
                                                          Double average){
        return StudentInfo.builder()
                .infoNote(studentInfoRequest.getInfoNote())
                .absentee(studentInfoRequest.getAbsentee())
                .midtermExam(studentInfoRequest.getMidtermExam())
                .finalExam(studentInfoRequest.getFinalExam())
                .id(studentInfoRequest.getStudentId())
                .examAverage(average)
                .letterGrade(note)
                .build();
    }
    public StudentInfo mapStudentInfoUpdateToStudentInfo(UpdateStudentInfoRequest studentInfoRequest,
                                                         Long studentInfoRequestId,
                                                         Lesson lesson,
                                                         EducationTerm educationTerm,
                                                         Note note,
                                                         Double average){
        return StudentInfo.builder()
                .id(studentInfoRequestId)
                .infoNote(studentInfoRequest.getInfoNote())
                .midtermExam(studentInfoRequest.getMidtermExam())
                .finalExam(studentInfoRequest.getFinalExam())
                .absentee(studentInfoRequest.getAbsentee())
                .lesson(lesson)
                .educationTerm(educationTerm)
                .examAverage(average)
                .letterGrade(note)
                .build();
    }
    public StudentInfoResponse mapStudentInfoToStudentInfoResponse(StudentInfo studentInfo){

        return StudentInfoResponse.builder()
                .lessonName(studentInfo.getLesson().getLessonName())
                .creditScore(studentInfo.getLesson().getCreditScore())
                .isCompulsory(studentInfo.getLesson().getIsCompulsory())
                .educationTerm(studentInfo.getEducationTerm().getTerm())
                .id(studentInfo.getId())
                .absentee(studentInfo.getAbsentee())
                .midTermExam(studentInfo.getMidtermExam())
                .finalExam(studentInfo.getFinalExam())
                .infoNote(studentInfo.getInfoNote())
                .note(studentInfo.getLetterGrade())
                .average(studentInfo.getExamAverage())
                .studentResponse(studentDto.mapStudentToStudentResponse(studentInfo.getStudent()))
                .build();
    }

}
