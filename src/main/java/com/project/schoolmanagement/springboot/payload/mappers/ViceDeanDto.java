package com.project.schoolmanagement.springboot.payload.mappers;

import com.project.schoolmanagement.springboot.entity.concretes.ViceDean;
import com.project.schoolmanagement.springboot.entity.enums.RoleType;
import com.project.schoolmanagement.springboot.payload.reponse.ViceDeanResponse;
import com.project.schoolmanagement.springboot.payload.request.ViceDeanRequest;
import com.project.schoolmanagement.springboot.service.UserRoleService;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ViceDeanDto {

    private final UserRoleService userRoleService;
    public ViceDean mapViceDeanRequestToViceDean(ViceDeanRequest viceDeanRequest){

        return ViceDean.builder()
                .username(viceDeanRequest.getUsername())
                .ssn(viceDeanRequest.getSsn())
                .name(viceDeanRequest.getName())
                .lastname(viceDeanRequest.getLastname())
                .birthday(viceDeanRequest.getBirthday())
                .birthPlace(viceDeanRequest.getBirthPlace())
                .password(viceDeanRequest.getPassword())
                .phoneNumber(viceDeanRequest.getPhoneNumber())
                .gender(viceDeanRequest.getGender())
                .build();
    }
    public ViceDeanResponse mapViceDeanToViceDeanResponse(ViceDean viceDean){
        return ViceDeanResponse.builder()
                .userId(viceDean.getId())
                .username(viceDean.getUsername())
                .name(viceDean.getName())
                .lastname(viceDean.getLastname())
                .birthday(viceDean.getBirthday())
                .birthPlace(viceDean.getBirthPlace())
                .phoneNumber(viceDean.getPhoneNumber())
                .ssn(viceDean.getSsn())
                .gender(viceDean.getGender())
                .build();
    }

    public ViceDean mapViceDeanRequestToUpdatedViceDean(ViceDeanRequest viceDeanRequest, Long viceDeanId){
        return ViceDean.builder()
                .id(viceDeanId)
                .username(viceDeanRequest.getUsername())
                .password(viceDeanRequest.getPassword())
                .name(viceDeanRequest.getName())
                .lastname(viceDeanRequest.getLastname())
                .ssn(viceDeanRequest.getSsn())
                // forgot ssn at first, then giving NullPointerExc  ?
                .birthday(viceDeanRequest.getBirthday())
                .birthPlace(viceDeanRequest.getBirthPlace())
                .phoneNumber(viceDeanRequest.getPhoneNumber())
                .gender(viceDeanRequest.getGender())
                .userRole(userRoleService.getUserRole(RoleType.ASSISTANT_MANAGER))
                .build();
    }
}
