package com.project.schoolmanagement.springboot.payload.mappers;

import com.project.schoolmanagement.springboot.entity.concretes.Lesson;
import com.project.schoolmanagement.springboot.payload.reponse.LessonResponse;
import com.project.schoolmanagement.springboot.payload.request.LessonRequest;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class LessonDto {

    public Lesson mapLessonRequestToLesson(LessonRequest lessonRequest){

        return Lesson.builder()
                .lessonName(lessonRequest.getLessonName())
                .creditScore(lessonRequest.getCreditScore())
                .isCompulsory(lessonRequest.getIsCompulsory())
                .build();
    }

    public LessonResponse mapLessonToLessonResponse(Lesson lesson){

        return LessonResponse.builder()
                .lessonId(lesson.getLessonId())
                .lessonName(lesson.getLessonName())
                .creditScore(lesson.getCreditScore())
                .isCompulsory(lesson.getIsCompulsory())
                .build();
    }


}
