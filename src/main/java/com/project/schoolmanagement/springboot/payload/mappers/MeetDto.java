package com.project.schoolmanagement.springboot.payload.mappers;


import com.project.schoolmanagement.springboot.entity.concretes.Meet;
import com.project.schoolmanagement.springboot.payload.reponse.MeetResponse;
import com.project.schoolmanagement.springboot.payload.request.MeetRequest;
import com.project.schoolmanagement.springboot.payload.request.UpdateMeetRequest;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Data
@Component
public class MeetDto {

    public Meet mapMeetRequestToMeet(MeetRequest meetRequest){

        return Meet.builder()
                .description(meetRequest.getDescription())
                .date(meetRequest.getDate())
                .startTime(meetRequest.getStartTime())
                .stopTime(meetRequest.getStopTime())
                //.studentList(meetRequest.getStudentIds())
                .build();
    }

    public Meet mapMeetUpdateRequestToMeet(UpdateMeetRequest updateMeetRequest, Long meetId){
        return Meet.builder()
                .id(meetId)
                .startTime(updateMeetRequest.getStartTime())
                .stopTime(updateMeetRequest.getStopTime())
                .date(updateMeetRequest.getDate())
                .description(updateMeetRequest.getDescription())
                .build();
    }
    public MeetResponse mapMeetToMeetResponse(Meet meet){
        return MeetResponse.builder()
                .id(meet.getId())
                .date(meet.getDate())
                .startTime(meet.getStartTime())
                .stopTime(meet.getStopTime())
                .description(meet.getDescription())
                .advisorTeacherId(meet.getAdvisoryTeacher().getId())
                .teacherSsn(meet.getAdvisoryTeacher().getTeacher().getSsn())
                .teacherName(meet.getAdvisoryTeacher().getTeacher().getName())
                .students(meet.getStudentList())
                .build();
    }
}
