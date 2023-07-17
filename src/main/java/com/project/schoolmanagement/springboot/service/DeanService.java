package com.project.schoolmanagement.springboot.service;

import com.project.schoolmanagement.springboot.entity.concretes.Dean;
import com.project.schoolmanagement.springboot.entity.enums.RoleType;
import com.project.schoolmanagement.springboot.exception.ResourceNotFoundException;
import com.project.schoolmanagement.springboot.payload.mappers.DeanDto;
import com.project.schoolmanagement.springboot.payload.reponse.DeanResponse;
import com.project.schoolmanagement.springboot.payload.reponse.ResponseMessage;
import com.project.schoolmanagement.springboot.payload.request.DeanRequest;
import com.project.schoolmanagement.springboot.repository.DeanRepository;
import com.project.schoolmanagement.springboot.utility.CheckParameterUpdateMethod;
import com.project.schoolmanagement.springboot.utility.FieldControl;
import com.project.schoolmanagement.springboot.utility.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeanService {

    private final DeanRepository deanRepository;

    private final FieldControl fieldControl;

    private final DeanDto deanDto;

    private final UserRoleService userRoleService;

    private final PasswordEncoder passwordEncoder;


    // TODO use mapstruct in your 3rd repository
    public ResponseMessage<DeanResponse> save(DeanRequest deanRequest){
        fieldControl.checkDuplicate(deanRequest.getUsername(), deanRequest.getSsn(),deanRequest.getPhoneNumber());

        Dean dean = deanDto.mapDeanRequestToDean(deanRequest);
        dean.setUserRole(userRoleService.getUserRole(RoleType.MANAGER));
        dean.setPassword(passwordEncoder.encode(dean.getPassword()));

        Dean savedDean = deanRepository.save(dean);

        return ResponseMessage.<DeanResponse>builder()
                .message("Dean saved")
                .httpStatus(HttpStatus.CREATED)
                .object(deanDto.mapDeanToDeanResponse(savedDean))
                .build();
    }

    public ResponseMessage<DeanResponse> update(DeanRequest deanRequest, Long deanId) {

        Optional<Dean> dean = deanRepository.findById(deanId);

        if (!dean.isPresent()){
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE,deanId));
        } else if (CheckParameterUpdateMethod.checkUniqueProperties(dean.get(),deanRequest)) {
            fieldControl.checkDuplicate(deanRequest.getUsername(),
                                        deanRequest.getSsn(),
                                        deanRequest.getPhoneNumber());

        }
        Dean updatedDean = deanDto.mapDeanRequestToUpdatedDean(deanRequest, deanId);
        updatedDean.setPassword(passwordEncoder.encode(deanRequest.getPassword()));
        deanRepository.save(updatedDean);
        return ResponseMessage.<DeanResponse> builder()
                .message("Dean updated successfully")
                .httpStatus(HttpStatus.OK)
                .object(deanDto.mapDeanToDeanResponse(updatedDean))
                .build();
    }

    public Optional<Dean> isDeanExist(Long deanId){
        Optional<Dean> dean = deanRepository.findById(deanId);
        if(deanRepository.findById(deanId).isEmpty()){
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE,deanId));
        }else {
            return dean;
        }
    }

    public ResponseMessage<?> deleteDeanById(Long deanId) {
        isDeanExist(deanId);


        return null;
    }
}
