package com.project.schoolmanagement.springboot.service;


import com.project.schoolmanagement.springboot.entity.concretes.EducationTerm;
import com.project.schoolmanagement.springboot.entity.concretes.Lesson;
import com.project.schoolmanagement.springboot.entity.concretes.Student;
import com.project.schoolmanagement.springboot.entity.concretes.Teacher;
import com.project.schoolmanagement.springboot.exception.ConflictException;
import com.project.schoolmanagement.springboot.payload.reponse.ResponseMessage;
import com.project.schoolmanagement.springboot.payload.reponse.StudentInfoResponse;
import com.project.schoolmanagement.springboot.payload.request.StudentInfoRequest;
import com.project.schoolmanagement.springboot.repository.StudentInfoRepository;
import com.project.schoolmanagement.springboot.utility.Messages;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentInfoService {

    private final StudentService studentService;
    private final TeacherService teacherService;
    private final LessonService lessonService;
    private final EducationTermService educationTermService;
    private final StudentInfoRepository studentInfoRepository;

    @Value("${midterm.exam.impact.percentage}")
    private Double midtermExamPercentage;
    @Value("${final.exam.impact.percentage}")
    private Double finalExamPercentage;
    public ResponseMessage<StudentInfoResponse> saveStudentInfo(String teacherUsername, StudentInfoRequest studentInfoRequest) {

        Student student = studentService.isStudentExist(studentInfoRequest.getStudentId());
        Teacher teacher = teacherService.getTeacherByUsername(teacherUsername);
        Lesson lesson = lessonService.isLessonExistById(studentInfoRequest.getLessonId());
        // does student have only one lesson according to lesson name
        checkSameLesson(studentInfoRequest.getStudentId(), lesson.getLessonName());



        EducationTerm educationTerm =

    }
    private void checkSameLesson(Long studentId, String lessonName){
        boolean isLessonDuplicationExist = studentInfoRepository.getAllByStudentId_Id(studentId)
                .stream()
                .anyMatch((e)-> e.getLesson().getLessonName().equalsIgnoreCase(lessonName));

        if (isLessonDuplicationExist){
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_LESSON_MESSAGE,lessonName));
        }
    }
    private Double calculateExamAverage(Double midtermExam, Double finalExam){

        return ((midtermExam*midtermExamPercentage) + (finalExam*finalExamPercentage));
    }
    private
}
