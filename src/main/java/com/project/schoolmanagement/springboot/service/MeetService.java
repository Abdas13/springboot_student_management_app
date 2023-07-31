package com.project.schoolmanagement.springboot.service;

import com.project.schoolmanagement.springboot.entity.concretes.AdvisoryTeacher;
import com.project.schoolmanagement.springboot.exception.BadRequestException;
import com.project.schoolmanagement.springboot.payload.reponse.MeetResponse;
import com.project.schoolmanagement.springboot.payload.reponse.ResponseMessage;
import com.project.schoolmanagement.springboot.payload.request.MeetRequest;
import com.project.schoolmanagement.springboot.repository.MeetRepository;
import com.project.schoolmanagement.springboot.repository.StudentRepository;
import com.project.schoolmanagement.springboot.utility.Messages;
import com.project.schoolmanagement.springboot.utility.ServiceHelpers;
import com.project.schoolmanagement.springboot.utility.TimeControl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeetService {

    private final MeetRepository meetRepository;
    private final StudentRepository studentRepository;
    private final ServiceHelpers serviceHelpers;
    private final AdvisoryTeacherService advisoryTeacherService;
    private final StudentService studentService;
    public ResponseMessage<MeetResponse> saveMeet(String username, MeetRequest meetRequest) {

        AdvisoryTeacher advisoryTeacher = advisoryTeacherService.getAdvisorTeacherByUsername(username);
        if (TimeControl.checkTime(meetRequest.getStartTime(), meetRequest.getStopTime())){
            throw new BadRequestException(Messages.TIME_NOT_VALID_MESSAGE);
        }
        TimeControl.checkTimeWithException(meetRequest.getStartTime(), meetRequest.getStopTime());

        return null;
    }
}
