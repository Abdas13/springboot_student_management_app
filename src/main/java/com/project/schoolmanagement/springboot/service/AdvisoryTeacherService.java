package com.project.schoolmanagement.springboot.service;

import com.project.schoolmanagement.springboot.entity.concretes.AdvisoryTeacher;
import com.project.schoolmanagement.springboot.entity.concretes.Teacher;
import com.project.schoolmanagement.springboot.entity.enums.RoleType;
import com.project.schoolmanagement.springboot.payload.mappers.AdvisoryTeacherDto;
import com.project.schoolmanagement.springboot.repository.AdvisoryTeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
