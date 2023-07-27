package com.project.schoolmanagement.springboot.service;

import com.project.schoolmanagement.springboot.entity.concretes.AdvisoryTeacher;
import com.project.schoolmanagement.springboot.entity.concretes.Teacher;
import com.project.schoolmanagement.springboot.entity.enums.RoleType;
import com.project.schoolmanagement.springboot.exception.ResourceNotFoundException;
import com.project.schoolmanagement.springboot.payload.mappers.AdvisoryTeacherDto;
import com.project.schoolmanagement.springboot.repository.AdvisoryTeacherRepository;
import com.project.schoolmanagement.springboot.utility.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdvisoryTeacherService {

    private final AdvisoryTeacherRepository advisoryTeacherRepository;

    private final UserRoleService userRoleService;

    private final AdvisoryTeacherDto advisoryTeacherDto;
    public void saveAdvisoryTeacher(Teacher teacher) {

        AdvisoryTeacher advisoryTeacher = advisoryTeacherDto.mapTeacherToAdvisoryTeacher(teacher);
        advisoryTeacher.setUserRole(userRoleService.getUserRole(RoleType.ADVISORY_TEACHER));

        advisoryTeacherRepository.save(advisoryTeacher);

    }

    public void updateAdvisoryTeacher(boolean status, Teacher teacher) {

        Optional<AdvisoryTeacher> advisoryTeacher = advisoryTeacherRepository.getAdvisoryTeacherByTeacher_Id(teacher.getId());

        AdvisoryTeacher.AdvisoryTeacherBuilder advisoryTeacherBuilder =
               AdvisoryTeacher.builder()
                       .teacher(teacher)
                       .userRole(userRoleService.getUserRole(RoleType.ADVISORY_TEACHER));

        //do we really have an advisory teacher in DB
        if(advisoryTeacher.isPresent()){
            //will be this new updated teacher really an advisory teacher
            if(status){
                advisoryTeacherBuilder.id(advisoryTeacher.get().getId());
                advisoryTeacherRepository.save(advisoryTeacherBuilder.build());
            } else {
                //these teacher is not advisory teacher anymore
                advisoryTeacherRepository.deleteById(advisoryTeacher.get().getId());
            }
        }
    }
    public AdvisoryTeacher getAdvisoryTeacherById(Long advisoryTeacherId){

        return advisoryTeacherRepository.findById(advisoryTeacherId).orElseThrow(
                ()-> new ResourceNotFoundException(String.format(Messages.NOT_FOUND_ADVISOR_MESSAGE,advisoryTeacherId)));
    }
}
