package com.project.schoolmanagement.springboot.service;

import com.project.schoolmanagement.springboot.payload.reponse.MeetResponse;
import com.project.schoolmanagement.springboot.payload.reponse.ResponseMessage;
import com.project.schoolmanagement.springboot.payload.request.MeetRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeetService {
    public ResponseMessage<MeetResponse> saveMeet(String username, MeetRequest meetRequest) {
    }
}
