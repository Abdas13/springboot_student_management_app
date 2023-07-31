package com.project.schoolmanagement.springboot.service;

import com.project.schoolmanagement.springboot.entity.concretes.AdvisoryTeacher;
import com.project.schoolmanagement.springboot.entity.concretes.Meet;
import com.project.schoolmanagement.springboot.entity.concretes.Student;
import com.project.schoolmanagement.springboot.exception.ConflictException;
import com.project.schoolmanagement.springboot.exception.ResourceNotFoundException;
import com.project.schoolmanagement.springboot.payload.mappers.MeetDto;
import com.project.schoolmanagement.springboot.payload.reponse.MeetResponse;
import com.project.schoolmanagement.springboot.payload.reponse.ResponseMessage;
import com.project.schoolmanagement.springboot.payload.reponse.StudentInfoResponse;
import com.project.schoolmanagement.springboot.payload.request.MeetRequest;
import com.project.schoolmanagement.springboot.payload.request.UpdateMeetRequest;
import com.project.schoolmanagement.springboot.repository.MeetRepository;
import com.project.schoolmanagement.springboot.repository.StudentRepository;
import com.project.schoolmanagement.springboot.utility.Messages;
import com.project.schoolmanagement.springboot.utility.ServiceHelpers;
import com.project.schoolmanagement.springboot.utility.TimeControl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetService {

    private final MeetRepository meetRepository;
    private final StudentRepository studentRepository;
    private final ServiceHelpers serviceHelpers;
    private final AdvisoryTeacherService advisoryTeacherService;
    private final StudentService studentService;
    private final MeetDto meetDto;
    public ResponseMessage<MeetResponse> saveMeet(String username, MeetRequest meetRequest) {

        AdvisoryTeacher advisoryTeacher = advisoryTeacherService.getAdvisorTeacherByUsername(username);

        TimeControl.checkTimeWithException(meetRequest.getStartTime(), meetRequest.getStopTime());

        for (Long studentId: meetRequest.getStudentIds()){
            studentService.isStudentExist(studentId);
            checkMeetConflict(studentId, meetRequest.getDate(), meetRequest.getStartTime(), meetRequest.getStopTime());
        }

        List<Student> students = studentService.getStudentsById(meetRequest.getStudentIds());
        Meet meet = new Meet();
        meet.setDate(meetRequest.getDate());
        meet.setStartTime(meetRequest.getStartTime());
        meet.setStopTime(meetRequest.getStopTime());
        meet.setStudentList(students);
        meet.setDescription(meetRequest.getDescription());
        meet.setAdvisoryTeacher(advisoryTeacher);
        Meet savedMeet = meetRepository.save(meet);
        return ResponseMessage.<MeetResponse> builder()
                .message("Meet successfully saved")
                .httpStatus(HttpStatus.CREATED)
                .object(meetDto.mapMeetToMeetResponse(savedMeet))
                .build();
    }

    private void checkMeetConflict(Long studentId, LocalDate date, LocalTime startTime, LocalTime stopTime){
        List<Meet> meets = meetRepository.findByStudentList_IdEquals(studentId);
        for (Meet meet : meets){
            LocalTime existingStartTime = meet.getStartTime();
            LocalTime existingStopTime = meet.getStopTime();

            if (meet.getDate().equals(date) &&
                     ((startTime.isAfter(existingStartTime) && startTime.isBefore(existingStopTime)) ||
                        (stopTime.isAfter(existingStartTime) && stopTime.isBefore(existingStopTime) )||
                        ( startTime.isBefore(existingStartTime) && stopTime.isAfter(existingStopTime) )||
                        ( startTime.equals(existingStartTime) && stopTime.equals(existingStopTime)))){
                throw new ConflictException("meet hours has conflict with existing meets");
            }
        }
    }

    public List<MeetResponse> getAll() {
        return meetRepository.findAll()
                .stream()
                .map(meetDto::mapMeetToMeetResponse)
                .collect(Collectors.toList());


    }

    public ResponseMessage<MeetResponse> getMeetById(Long meetId) {

        return ResponseMessage.<MeetResponse>builder()
                .message("Meet found successfully")
                .httpStatus(HttpStatus.OK)
                .object(meetDto.mapMeetToMeetResponse(isMeetExistById(meetId)))
                .build();
    }
    private Meet isMeetExistById(Long meetId){
       return meetRepository.findById(meetId).orElseThrow(
                ()-> new ResourceNotFoundException(String.format(Messages.MEET_NOT_FOUND_MESSAGE, meetId)));
    }

    public ResponseMessage delete(Long meetId) {
        isMeetExistById(meetId);
        meetRepository.deleteById(meetId);

        return ResponseMessage.builder()
                .message("Meet deleted successfully")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public ResponseMessage<MeetResponse> updateMeet(UpdateMeetRequest updateMeetRequest, Long meetId) {

        Meet meet = isMeetExistById(meetId);
        TimeControl.checkTimeWithException(updateMeetRequest.getStartTime(),updateMeetRequest.getStopTime());

        if (!(meet.getDate().equals(updateMeetRequest.getDate())  &&
                meet.getStartTime().equals(updateMeetRequest.getStartTime()) &&
                meet.getStopTime().equals(updateMeetRequest.getStopTime()))){
            for (Long studentId: updateMeetRequest.getStudentIds()){
                checkMeetConflict(studentId,updateMeetRequest.getDate(),
                        updateMeetRequest.getStartTime(),
                        updateMeetRequest.getStopTime());
            }
        }
        List<Student> students = studentService.getStudentsById(updateMeetRequest.getStudentIds());
        Meet updateMeet = meetDto.mapMeetUpdateRequestToMeet(updateMeetRequest, meetId);
        updateMeet.setStudentList(students);
        updateMeet.setAdvisoryTeacher(meet.getAdvisoryTeacher());
        Meet updatedAndSavedMeet = meetRepository.save(updateMeet);

        return ResponseMessage.<MeetResponse>builder()
                .message("Meet updated")
                .httpStatus(HttpStatus.OK)
                .object(meetDto.mapMeetToMeetResponse(updatedAndSavedMeet))
                .build();
    }
    public ResponseEntity<List<MeetResponse>> getAllMeetByTeacher(HttpServletRequest httpServletRequest) {
        String username  = (String) httpServletRequest.getAttribute("username");
        AdvisoryTeacher advisoryTeacher = advisoryTeacherService.getAdvisorTeacherByUsername(username);
        List<MeetResponse> meetResponseList = meetRepository.getByAdvisoryTeacher_idEquals(advisoryTeacher.getId())
                .stream()
                .map(meetDto::mapMeetToMeetResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(meetResponseList);


    }

    public List<MeetResponse> getAllMeetByStudent(HttpServletRequest httpServletRequest) {

        String username = (String) httpServletRequest.getAttribute("username");
        Student student = studentService.isStudentsExistByUsername(username);

        return meetRepository.findByStudentList_IdEquals(student.getId())
                .stream()
                .map(meetDto::mapMeetToMeetResponse)
                .collect(Collectors.toList());


    }

    public Page<MeetResponse> search(int page, int size) {
        Pageable pageable = serviceHelpers.getPageableWithProperties(page, size);
        return meetRepository.findAll(pageable)
                .map(meetDto::mapMeetToMeetResponse);
    }
}
