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
import com.project.schoolmanagement.springboot.utility.ServiceHelpers;
import com.project.schoolmanagement.springboot.utility.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeanService {

    private final DeanRepository deanRepository;

    private final ServiceHelpers serviceHelpers;

    private final DeanDto deanDto;

    private final UserRoleService userRoleService;

    private final PasswordEncoder passwordEncoder;


    // TODO use mapstruct in your 3rd repository
    public ResponseMessage<DeanResponse> save(DeanRequest deanRequest){
        serviceHelpers.checkDuplicate(deanRequest.getUsername(), deanRequest.getSsn(),deanRequest.getPhoneNumber());

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

        Optional<Dean> dean = isDeanExist(deanId);

        if (!dean.isPresent()){
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE,deanId));
        } else if (CheckParameterUpdateMethod.checkUniqueProperties(dean.get(),deanRequest)) {
            serviceHelpers.checkDuplicate(deanRequest.getUsername(),
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
        if(!deanRepository.findById(deanId).isPresent()){
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE,deanId));
        }else {
            return dean;
        }
    }

    public ResponseMessage<?> deleteDeanById(Long deanId) {
        isDeanExist(deanId);

        deanRepository.deleteById(deanId);

        return ResponseMessage.builder()
                .message("Dean deleted")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public ResponseMessage<DeanResponse> getDeanById(Long deanId) {

        return ResponseMessage.<DeanResponse>builder()
                .message("Dean Successfully found")
                .httpStatus(HttpStatus.OK)
                .object(deanDto.mapDeanToDeanResponse(isDeanExist(deanId).get()))
                .build();


    }

    public List<DeanResponse> getAllDeans() {

        return deanRepository.findAll()
                .stream()
                .map(deanDto::mapDeanToDeanResponse)
                .collect(Collectors.toList());
    }

    public Page<DeanResponse> getAllDeansByPage(int page, int size, String sort, String type) {

        Pageable pageable = serviceHelpers.getPageableWithProperties(page, size, sort, type);
        return deanRepository.findAll(pageable).map(deanDto::mapDeanToDeanResponse);
    }
}
