package com.project.schoolmanagement.springboot.service;

import com.project.schoolmanagement.springboot.entity.concretes.EducationTerm;
import com.project.schoolmanagement.springboot.exception.ResourceNotFoundException;
import com.project.schoolmanagement.springboot.payload.mappers.EducationTermDto;
import com.project.schoolmanagement.springboot.payload.reponse.EducationTermResponse;
import com.project.schoolmanagement.springboot.payload.reponse.ResponseMessage;
import com.project.schoolmanagement.springboot.payload.request.EducationTermRequest;
import com.project.schoolmanagement.springboot.repository.EducationTermRepository;
import com.project.schoolmanagement.springboot.utility.Messages;
import com.project.schoolmanagement.springboot.utility.ServiceHelpers;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EducationTermService {

    private final EducationTermRepository educationTermRepository;

    private final ServiceHelpers serviceHelpers;
    private final EducationTermDto educationTermDto;
    public ResponseMessage<EducationTermResponse> saveEducationTerm(EducationTermRequest educationTermRequest) {

        checkEducationTermDate(educationTermRequest);

        EducationTerm savedEducationTerm = educationTermRepository.save(educationTermDto.mapEducationTermRequestToEducationTerm(educationTermRequest));

        return ResponseMessage.<EducationTermResponse> builder()
                .message("Education term saved")
                .httpStatus(HttpStatus.CREATED)
                .object(educationTermDto.mapEducationTermToEducationTermResponse(savedEducationTerm))
                .build();

    }

    private void checkEducationTermDate(EducationTermRequest educationTermRequest){

        if (educationTermRequest.getLastRegistrationDate().isAfter(educationTermRequest.getStartDate())){
            throw new ResourceNotFoundException(Messages.EDUCATION_START_DATE_IS_EARLIER_THAN_LAST_REGISTRATION_DATE);
        }
        if (educationTermRequest.getEndDate().isBefore(educationTermRequest.getStartDate())){
            throw new ResourceNotFoundException(Messages.EDUCATION_END_DATE_IS_EARLIER_THAN_START_DATE);
        }if (educationTermRepository.existsByTermAndYear(educationTermRequest.getTerm(), educationTermRequest.getStartDate().getYear())){
        throw new ResourceNotFoundException(Messages.EDUCATION_TERM_IS_ALREADY_EXIST_BY_TERM_AND_YEAR_MESSAGE);
        }
    }

    public EducationTerm getEducationTermById(Long id) {

        return isEducationTermExist(id);
    }

    private EducationTerm isEducationTermExist(Long id) {

        EducationTerm educationTerm = educationTermRepository.findByIdEquals(id);

        if (educationTerm==null){
            throw new ResourceNotFoundException(String.format(Messages.EDUCATION_TERM_NOT_FOUND_MESSAGE,id));
        }else {
            return educationTerm;
        }

    }

    public List<EducationTermResponse> getAllEducationTerms() {

        return educationTermRepository.findAll()
                .stream()
                .map(educationTermDto::mapEducationTermToEducationTermResponse)
                .collect(Collectors.toList());
    }

    public Page<EducationTermResponse> getAllEducationTermsByPage(int page, int size, String sort, String type) {

        Pageable pageable = serviceHelpers.getPageableWithProperties(page, size, sort, type);

        return educationTermRepository.findAll(pageable)
                .map(educationTermDto::mapEducationTermToEducationTermResponse);
    }

    public ResponseMessage<?> deleteEducationTermById(Long id) {

        isEducationTermExist(id);

        educationTermRepository.deleteById(id);

        return ResponseMessage.builder()
                .message("Education term deleted successfully")
                .httpStatus(HttpStatus.OK)
                .build();

    }

    public ResponseMessage<EducationTermResponse> updateEducationTerm(Long id, EducationTermRequest educationTermRequest) {

        isEducationTermExist(id);

        checkEducationTermDate(educationTermRequest);

        EducationTerm updatedEducationTerm = educationTermDto.mapEducationTermRequestToUpdatedEducationTerm(id, educationTermRequest);

        EducationTerm savedEducationTerm = educationTermRepository.save(updatedEducationTerm);

        return ResponseMessage.<EducationTermResponse>builder()
                .message("Education term updated successfully")
                .httpStatus(HttpStatus.OK)
                .object(educationTermDto.mapEducationTermToEducationTermResponse(savedEducationTerm))
                .build();
    }

}
