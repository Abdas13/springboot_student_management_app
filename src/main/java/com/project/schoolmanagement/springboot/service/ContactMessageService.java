package com.project.schoolmanagement.springboot.service;

import com.project.schoolmanagement.springboot.entity.concretes.ContactMessage;
import com.project.schoolmanagement.springboot.payload.reponse.ContactMessageResponse;
import com.project.schoolmanagement.springboot.payload.reponse.ResponseMessage;
import com.project.schoolmanagement.springboot.payload.request.ContactMessageRequest;
import com.project.schoolmanagement.springboot.repository.ContactMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ContactMessageService {

    private ContactMessageRepository contactMessageRepository;

    public ResponseMessage<ContactMessageResponse> save(ContactMessageRequest contactMessageRequest){

        //it is expected to create one message in a day with the same email


        ContactMessage contactMessage = createContactMessage(contactMessageRequest);
        ContactMessage savedData = contactMessageRepository.save(contactMessage);
        return ResponseMessage.<ContactMessageResponse>builder()
                .message("Contact message created successfully")
                .httpStatus(HttpStatus.CREATED)
                .object(createResponse(savedData))
                .build();

    }
    // mapContactMessageToContactMessageResponse name would be better
    private ContactMessageResponse createResponse(ContactMessage contactMessage){
        return ContactMessageResponse.builder()
                .name(contactMessage.getName())
                .subject(contactMessage.getSubject())
                .message(contactMessage.getMessage())
                .email(contactMessage.getEmail())
                .date(LocalDate.now())
                .build();


    }

    // mapContactMessageRequestToContactMessage name would be better.
    private ContactMessage createContactMessage(ContactMessageRequest contactMessageRequest){
        return ContactMessage.builder()
                .name(contactMessageRequest.getName())
                .subject(contactMessageRequest.getSubject())
                .message(contactMessageRequest.getMessage())
                .email(contactMessageRequest.getEmail())
                .date(LocalDate.now())
                .build();
    }


}
