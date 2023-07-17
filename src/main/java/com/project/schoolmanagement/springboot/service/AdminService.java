package com.project.schoolmanagement.springboot.service;

import com.project.schoolmanagement.springboot.entity.concretes.Admin;
import com.project.schoolmanagement.springboot.entity.concretes.UserRole;
import com.project.schoolmanagement.springboot.entity.enums.RoleType;
import com.project.schoolmanagement.springboot.exception.ConflictException;
import com.project.schoolmanagement.springboot.payload.reponse.AdminResponse;
import com.project.schoolmanagement.springboot.payload.reponse.ResponseMessage;
import com.project.schoolmanagement.springboot.payload.request.AdminRequest;
import com.project.schoolmanagement.springboot.repository.*;
import com.project.schoolmanagement.springboot.utility.FieldControl;
import com.project.schoolmanagement.springboot.utility.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

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

    private final FieldControl fieldControl;


    public ResponseMessage save(AdminRequest adminRequest){

        fieldControl.checkDuplicate(adminRequest.getUsername(), adminRequest.getSsn(), adminRequest.getPhoneNumber());

        Admin admin = mapAdminRequestToAdmin(adminRequest);
        admin.setBuilt_in(false);

        // if username is also Admin we are setting built_in prop. to FALSE
        if (Objects.equals(adminRequest.getUsername(), "Admin")){
            admin.setBuilt_in(true);
        }

       admin.setUserRole(userRoleService.getUserRole(RoleType.ADMIN));
        //
        // we will implement password here
        Admin savedAdmin = adminRepository.save(admin);

        // in response message savedAdmin instance may not be sent back to front-end
        return ResponseMessage.<AdminResponse> builder()
                .message("Admin saved")
                .httpStatus(HttpStatus.CREATED)
                .object(mapAdminToAdminResponse(savedAdmin))
                .build();
    }

    private AdminResponse mapAdminToAdminResponse(Admin admin){
        return AdminResponse.builder()
                .userId(admin.getId())
                .username(admin.getUsername())
                .birthday(admin.getBirthday())
                .birthPlace(admin.getBirthPlace())
                .name(admin.getName())
                .lastname(admin.getLastname())
                .phoneNumber(admin.getPhoneNumber())
                .gender(admin.getGender())
                .ssn(admin.getSsn())
                .build();
    }

    private Admin mapAdminRequestToAdmin(AdminRequest adminRequest){
        return Admin.builder()
                .username(adminRequest.getUsername())
                .name(adminRequest.getName())
                .lastname(adminRequest.getLastname())
                .password(adminRequest.getPassword())
                .ssn(adminRequest.getSsn())
                .birthday(adminRequest.getBirthday())
                .birthPlace(adminRequest.getBirthPlace())
                .phoneNumber(adminRequest.getPhoneNumber())
                .gender(adminRequest.getGender())
                .build();
    }



    public Page<Admin> getAllAdmins(Pageable pageable) {
        return adminRepository.findAll(pageable);
    }

    public String deleteAdmin(Long id) {
        //we should check the database if it really exists
        Optional<Admin> admin = adminRepository.findById(id);
        //TODO please divide the cases and throw meaningful response messages
        if(admin.isPresent() && admin.get().isBuilt_in()){
            throw new ConflictException(Messages.NOT_PERMITTED_METHOD_MESSAGE);
        }

        if (admin.isPresent()){
            adminRepository.deleteById(id);
            //TODO move this hard coded part to Messages class and call this property.
            return "Admin is deleted successfully";
        }

        return String.format(Messages.NOT_FOUND_USER_MESSAGE,id);

    }
    public long countAllAdmins(){
        return adminRepository.count();
    }
}
