package com.project.schoolmanagement.springboot.service;


import com.project.schoolmanagement.springboot.entity.concretes.ViceDean;
import com.project.schoolmanagement.springboot.entity.enums.RoleType;
import com.project.schoolmanagement.springboot.exception.ResourceNotFoundException;
import com.project.schoolmanagement.springboot.payload.mappers.ViceDeanDto;
import com.project.schoolmanagement.springboot.payload.reponse.ResponseMessage;
import com.project.schoolmanagement.springboot.payload.reponse.ViceDeanResponse;
import com.project.schoolmanagement.springboot.payload.request.ViceDeanRequest;
import com.project.schoolmanagement.springboot.repository.ViceDeanRepository;
import com.project.schoolmanagement.springboot.utility.CheckParameterUpdateMethod;
import com.project.schoolmanagement.springboot.utility.ServiceHelpers;
import com.project.schoolmanagement.springboot.utility.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ViceDeanService {

    private final ViceDeanRepository viceDeanRepository;
    public final ServiceHelpers serviceHelpers;
    public final ViceDeanDto viceDeanDto;
    public final PasswordEncoder passwordEncoder;
    public final UserRoleService userRoleService;
    public ResponseMessage<ViceDeanResponse> saveViceDean(ViceDeanRequest viceDeanRequest) {

        serviceHelpers.checkDuplicate(viceDeanRequest.getUsername(),
                viceDeanRequest.getSsn(),
                viceDeanRequest.getPhoneNumber());

        ViceDean viceDean = viceDeanDto.mapViceDeanRequestToViceDean(viceDeanRequest);

        viceDean.setPassword(passwordEncoder.encode(viceDeanRequest.getPassword()));
        viceDean.setUserRole(userRoleService.getUserRole(RoleType.ASSISTANT_MANAGER));

        ViceDean savedViceDean = viceDeanRepository.save(viceDean);

        return ResponseMessage.<ViceDeanResponse>builder()
                .message("Vice Dean saved. ")
                .httpStatus(HttpStatus.CREATED)
                .object(viceDeanDto.mapViceDeanToViceDeanResponse(savedViceDean))
                .build();
    }

    public ResponseMessage<ViceDeanResponse> getViceDeanById(Long userId) {

        return ResponseMessage.<ViceDeanResponse>builder()
                .message("Vice Dean found")
                .httpStatus(HttpStatus.OK)
                .object(viceDeanDto.mapViceDeanToViceDeanResponse(isViceDeanExist(userId).get()))
                .build();


    }

    private Optional<ViceDean> isViceDeanExist(Long viceDeanId) {

        Optional<ViceDean> viceDean = viceDeanRepository.findById(viceDeanId);

        if (!viceDeanRepository.findById(viceDeanId).isPresent()){
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE));
        }else {
            return viceDean;
        }
    }

    public ResponseMessage<?> deleteViceDeanById(Long viceDeanId) {

        isViceDeanExist(viceDeanId);
        viceDeanRepository.deleteById(viceDeanId);

        return ResponseMessage.builder()
                .message("Vice Dean deleted")
                .httpStatus(HttpStatus.OK)
                .build();

    }

    public ResponseMessage<ViceDeanResponse> updateViceDean(ViceDeanRequest viceDeanRequest,
                                                            Long viceDeanId) {

        Optional<ViceDean> viceDean = isViceDeanExist(viceDeanId);

        if (CheckParameterUpdateMethod.checkUniqueProperties(viceDean.get(), viceDeanRequest)){
            serviceHelpers.checkDuplicate(viceDeanRequest.getPhoneNumber(),
                    viceDeanRequest.getSsn(),
                    viceDeanRequest.getUsername());
        }
        ViceDean updatedViceDean = viceDeanDto.mapViceDeanRequestToUpdatedViceDean(viceDeanRequest, viceDeanId);
        updatedViceDean.setPassword(passwordEncoder.encode(viceDeanRequest.getPassword()));
        ViceDean savedViceDean = viceDeanRepository.save(updatedViceDean);

        return ResponseMessage.<ViceDeanResponse> builder()
                .message("Vice Dean updated successfully")
                .httpStatus(HttpStatus.OK)
                .object(viceDeanDto.mapViceDeanToViceDeanResponse(savedViceDean))
                .build();

    }

    public List<ViceDeanResponse> getAllViceDeans() {

        return viceDeanRepository.findAll()
                .stream()
                .map(viceDeanDto::mapViceDeanToViceDeanResponse)
                .collect(Collectors.toList());
    }

    public Page<ViceDeanResponse> getAllDeansByPage(int page, int size, String sort, String type) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());

        if (Objects.equals(type, "desc")){
            pageable = PageRequest.of(page, size,Sort.by(sort).descending());
        }
        return viceDeanRepository.findAll(pageable)
                .map(viceDeanDto::mapViceDeanToViceDeanResponse);
    }
}
