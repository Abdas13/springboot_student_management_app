package com.project.schoolmanagement.springboot.payload.mappers;

import com.project.schoolmanagement.springboot.entity.concretes.Dean;
import com.project.schoolmanagement.springboot.entity.enums.RoleType;
import com.project.schoolmanagement.springboot.payload.reponse.DeanResponse;
import com.project.schoolmanagement.springboot.payload.request.DeanRequest;
import com.project.schoolmanagement.springboot.service.UserRoleService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
public class DeanDto {

    private UserRoleService userRoleService;
    public Dean mapDeanRequestToDean(DeanRequest deanRequest){

        return Dean.builder()
                .username(deanRequest.getUsername())
                .name(deanRequest.getName())
                .lastname(deanRequest.getLastname())
                .password(deanRequest.getPassword())
                .ssn(deanRequest.getSsn())
                .birthday(deanRequest.getBirthday())
                .birthPlace(deanRequest.getBirthPlace())
                .phoneNumber(deanRequest.getPhoneNumber())
                .gender(deanRequest.getGender())
                .build();

    }

    public DeanResponse mapDeanToDeanResponse(Dean dean){
        return DeanResponse.builder()
                .userId(dean.getId())
                .username(dean.getUsername())
                .name(dean.getName())
                .lastname(dean.getLastname())
                .birthday(dean.getBirthday())
                .birthPlace(dean.getBirthPlace())
                .phoneNumber(dean.getPhoneNumber())
                .gender(dean.getGender())
                .ssn(dean.getSsn())
                .build();

    }
    public Dean mapDeanRequestToUpdatedDean(DeanRequest deanRequest, Long managerId){
        return Dean.builder()
                .id(managerId)
                .username(deanRequest.getUsername())
                .ssn(deanRequest.getSsn())
                .name(deanRequest.getName())
                .lastname(deanRequest.getLastname())
                .birthday(deanRequest.getBirthday())
                .birthPlace(deanRequest.getBirthPlace())
                .phoneNumber(deanRequest.getPhoneNumber())
                .gender(deanRequest.getGender())
                .userRole(userRoleService.getUserRole(RoleType.MANAGER))
                .build();
    }
}
