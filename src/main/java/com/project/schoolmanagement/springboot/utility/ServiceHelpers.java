package com.project.schoolmanagement.springboot.utility;


import com.project.schoolmanagement.springboot.exception.ConflictException;
import com.project.schoolmanagement.springboot.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ServiceHelpers {

    private final AdminRepository adminRepository;

    private final DeanRepository deanRepository;

    private final ViceDeanRepository viceDeanRepository;

    private final StudentRepository studentRepository;

    private final TeacherRepository teacherRepository;

    private final GuestUserRepository guestUserRepository;


    public void checkDuplicate(String... values) {
        String username = values[0];
        String ssn = values[1];
        String phone = values[2];
        String email = "";

        if (values.length == 4) {
            email = values[3];
        }

        if (adminRepository.existsByUsername(username) || deanRepository.existsByUsername(username) ||
                studentRepository.existsByUsername(username) || teacherRepository.existsByUsername(username) ||
                viceDeanRepository.existsByUsername(username) || guestUserRepository.existsByUsername(username)) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_USERNAME, username));
        } else if (adminRepository.existsBySsn(ssn) || deanRepository.existsBySsn(ssn) ||
                studentRepository.existsBySsn(ssn) || teacherRepository.existsBySsn(ssn) ||
                viceDeanRepository.existsBySsn(ssn) || guestUserRepository.existsBySsn(ssn)) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_SSN, ssn));
        } else if (adminRepository.existsByPhoneNumber(phone) || deanRepository.existsByPhoneNumber(phone) ||
                studentRepository.existsByPhoneNumber(phone) || teacherRepository.existsByPhoneNumber(phone) ||
                viceDeanRepository.existsByPhoneNumber(phone) || guestUserRepository.existsByPhoneNumber(phone)) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_PHONE_NUMBER, phone));
        } else if (studentRepository.existsByEmail(email) || teacherRepository.existsByEmail(email)) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_SSN, email));
        }
    }
    public Pageable getPageableWithProperties(int page, int size, String sort, String type ){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());

        if (Objects.equals(type, "desc")){
            pageable = PageRequest.of(page, size,Sort.by(sort).descending());
        }
        return pageable;
    }

    // As a requirement all Admin, ViceAdmin, Dean, Student, Teacher, GuestUser
    // should have unique username, email, ssn, phone number
//    public void checkDuplicate(String username, String ssn, String phoneNumber){
//
//        if (adminRepository.existsByUsername(username) ||
//                deanRepository.existsByPhoneNumber(username) ||
//                studentRepository.existsByUsername(username) ||
//                teacherRepository.existsByUsername(username) ||
//                viceDeanRepository.existsByUsername(username) ||
//                guestUserRepository.existsByUsername(username)){
//            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_USERNAME,username));
//        } else if (adminRepository.existsBySsn(ssn) ||
//                deanRepository.existsBySsn(ssn) ||
//                studentRepository.existsBySsn(ssn) ||
//                teacherRepository.existsBySsn(ssn) ||
//                viceDeanRepository.existsBySsn(ssn) ||
//                guestUserRepository.existsBySsn(ssn)) {
//            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_SSN,ssn));
//        } else if (adminRepository.existsByPhoneNumber(phoneNumber) ||
//                deanRepository.existsByPhoneNumber(phoneNumber) ||
//                studentRepository.existsByPhoneNumber(phoneNumber) ||
//                teacherRepository.existsByPhoneNumber(phoneNumber) ||
//                viceDeanRepository.existsByPhoneNumber(phoneNumber) ||
//                guestUserRepository.existsByPhoneNumber(phoneNumber)) {
//            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_PHONE_NUMBER));
//        }
//
//    }
//    public void checkDuplicate(String username, String ssn, String phoneNumber, String email){
//
//        if (adminRepository.existsByUsername(username) ||
//                deanRepository.existsByPhoneNumber(username) ||
//                studentRepository.existsByUsername(username) ||
//                teacherRepository.existsByUsername(username) ||
//                viceDeanRepository.existsByUsername(username) ||
//                guestUserRepository.existsByUsername(username)){
//            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_USERNAME,username));
//        } else if (adminRepository.existsBySsn(ssn) ||
//                deanRepository.existsBySsn(ssn) ||
//                studentRepository.existsBySsn(ssn) ||
//                teacherRepository.existsBySsn(ssn) ||
//                viceDeanRepository.existsBySsn(ssn) ||
//                guestUserRepository.existsBySsn(ssn)) {
//            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_SSN,ssn));
//        } else if (adminRepository.existsByPhoneNumber(phoneNumber) ||
//                deanRepository.existsByPhoneNumber(phoneNumber) ||
//                studentRepository.existsByPhoneNumber(phoneNumber) ||
//                teacherRepository.existsByPhoneNumber(phoneNumber) ||
//                viceDeanRepository.existsByPhoneNumber(phoneNumber) ||
//                guestUserRepository.existsByPhoneNumber(phoneNumber)) {
//            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_PHONE_NUMBER));
//        }else if (studentRepository.existsByEmail(email) ||
//                teacherRepository.existsByEmail(email)) {
//            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_EMAIL));
//        }
//    }
}
