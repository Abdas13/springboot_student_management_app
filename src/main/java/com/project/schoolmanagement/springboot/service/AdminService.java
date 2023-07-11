package com.project.schoolmanagement.springboot.service;

import com.project.schoolmanagement.springboot.entity.concretes.Admin;
import com.project.schoolmanagement.springboot.entity.concretes.UserRole;
import com.project.schoolmanagement.springboot.entity.enums.RoleType;
import com.project.schoolmanagement.springboot.exception.ConflictException;
import com.project.schoolmanagement.springboot.payload.reponse.ResponseMessage;
import com.project.schoolmanagement.springboot.payload.request.AdminRequest;
import com.project.schoolmanagement.springboot.repository.*;
import com.project.schoolmanagement.springboot.utility.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    private final DeanRepository deanRepository;

    private final ViceDeanRepository viceDeanRepository;

    private final StudentRepository studentRepository;

    private final TeacherRepository teacherRepository;

    private final GuestUserRepository guestUserRepository;

    private final UserRoleService userRoleService;


    public ResponseMessage save(AdminRequest adminRequest){

        checkDuplicate(adminRequest.getUsername(), adminRequest.getSsn(), adminRequest.getPhoneNumber());

        Admin admin = mapAdminRequestToAdmin(adminRequest);
        admin.setBuilt_in(false);

        // if username is also Admin we are setting built_in prop. to FALSE
        if (Objects.equals(adminRequest.getName(), "Admin")){
            admin.setBuilt_in(false);
        }

       admin.setUserRole(userRoleService.getUserRole(RoleType.ADMIN));
        //
        return null;
    }

    private Admin mapAdminRequestToAdmin(AdminRequest adminRequest){
        return Admin.builder()
                .username(adminRequest.getUsername())
                .name(adminRequest.getName())
                .lastname(adminRequest.getLastname())
                .password(adminRequest.getPassword())
                .ssn(adminRequest.getSsn())
                .birthDay(adminRequest.getBirthday())
                .birthPlace(adminRequest.getBirthPlace())
                .phoneNumber(adminRequest.getPhoneNumber())
                .gender(adminRequest.getGender())
                .build();
    }

    // As a requirement all Admin, ViceAdmin, Dean, Student, Teacher, GuestUser
    // should have unique username, email, ssn, phone number

    public void checkDuplicate(String username, String ssn, String phoneNumber){

        if (adminRepository.existsByUsername(username) ||
                deanRepository.existsByPhoneNumber(username) ||
                studentRepository.existsByUsername(username) ||
                teacherRepository.existsByUsername(username) ||
                viceDeanRepository.existsByUsername(username) ||
                guestUserRepository.existsByUsername(username)){
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_USERNAME,username));
        } else if (adminRepository.existsBySsn(ssn) ||
                deanRepository.existsBySsn(ssn) ||
                studentRepository.existsBySsn(ssn) ||
                teacherRepository.existsBySsn(ssn) ||
                viceDeanRepository.existsBySsn(ssn) ||
                guestUserRepository.existsBySsn(ssn)) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_SSN,ssn));
        } else if (adminRepository.existsByPhoneNumber(phoneNumber) ||
                deanRepository.existsByPhoneNumber(phoneNumber) ||
                studentRepository.existsByPhoneNumber(phoneNumber) ||
                teacherRepository.existsByPhoneNumber(phoneNumber) ||
                viceDeanRepository.existsByPhoneNumber(phoneNumber) ||
                guestUserRepository.existsByPhoneNumber(phoneNumber)) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_PHONE_NUMBER));
        }

    }
}
