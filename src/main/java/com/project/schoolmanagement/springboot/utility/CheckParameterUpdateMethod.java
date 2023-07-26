package com.project.schoolmanagement.springboot.utility;

import com.project.schoolmanagement.springboot.entity.abstracts.User;
import com.project.schoolmanagement.springboot.entity.concretes.Student;
import com.project.schoolmanagement.springboot.entity.concretes.Teacher;
import com.project.schoolmanagement.springboot.payload.request.StudentRequest;
import com.project.schoolmanagement.springboot.payload.request.TeacherRequest;
import com.project.schoolmanagement.springboot.payload.request.abstracts.BaseUserRequest;

public class CheckParameterUpdateMethod {

    public static boolean checkUniqueProperties(User user, BaseUserRequest baseUserRequest){

        return user.getSsn().equalsIgnoreCase(baseUserRequest.getSsn())
                || user.getPhoneNumber().equalsIgnoreCase(baseUserRequest.getPhoneNumber())
                || user.getUsername().equalsIgnoreCase(baseUserRequest.getUsername());
    }
    public static boolean checkUniquePropertiesForTeacher(Teacher teacher, TeacherRequest teacherRequest){
        return checkUniqueProperties(teacher,teacherRequest)
                || teacher.getEmail().equalsIgnoreCase(teacherRequest.getEmail());
    }
    public static boolean checkUniquePropertiesForStudent(Student student, StudentRequest studentRequest){
        return checkUniqueProperties(student,studentRequest)
                || student.getEmail().equalsIgnoreCase(studentRequest.getEmail());
    }
}