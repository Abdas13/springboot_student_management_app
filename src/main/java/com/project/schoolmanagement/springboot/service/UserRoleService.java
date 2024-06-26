package com.project.schoolmanagement.springboot.service;

import com.project.schoolmanagement.springboot.entity.concretes.UserRole;
import com.project.schoolmanagement.springboot.entity.enums.RoleType;
import com.project.schoolmanagement.springboot.exception.ConflictException;
import com.project.schoolmanagement.springboot.repository.UserRoleRepository;
import com.project.schoolmanagement.springboot.utility.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRole getUserRole(RoleType roleType){


       // Optional<UserRole> userRole = userRoleRepository.findByEnumRoleEquals(roleType);


        /*
        check the Optional<> usage
         */
//        if (userRole.isPresent()){
//            return userRole.get();
//        }else {
//            throw new ConflictException(Messages.ROLE_NOT_FOUND)
//        }

        // second way
        return userRoleRepository.findByEnumRoleEquals(roleType)
                .orElseThrow(
                ()-> new ConflictException(Messages.ROLE_NOT_FOUND));
    }

    public List<UserRole> getAllUserRole(){
        return userRoleRepository.findAll();
    }

    public UserRole save(RoleType roleType){
        if (userRoleRepository.existsByEnumRoleEquals(roleType)){
            throw new ConflictException(Messages.ROLE_ALREADY_EXIST);
        }
        UserRole userRole  = UserRole.builder().roleType(roleType).build();

        return userRoleRepository.save(userRole);
    }


}
