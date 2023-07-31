package com.project.schoolmanagement.springboot.payload.mappers;


import com.project.schoolmanagement.springboot.entity.concretes.Meet;
import com.project.schoolmanagement.springboot.payload.request.MeetRequest;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class MeetDto {

    public Meet mapMeetRequestToMeet(MeetRequest meetRequest){

        return null;
    }
}
