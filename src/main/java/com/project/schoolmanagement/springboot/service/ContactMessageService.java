package com.project.schoolmanagement.springboot.service;

import com.project.schoolmanagement.springboot.entity.concretes.ContactMessage;
import com.project.schoolmanagement.springboot.exception.ConflictException;
import com.project.schoolmanagement.springboot.exception.ResourceNotFoundException;
import com.project.schoolmanagement.springboot.payload.reponse.ContactMessageResponse;
import com.project.schoolmanagement.springboot.payload.reponse.ResponseMessage;
import com.project.schoolmanagement.springboot.payload.request.ContactMessageRequest;
import com.project.schoolmanagement.springboot.repository.ContactMessageRepository;
import com.project.schoolmanagement.springboot.utility.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;

    public ResponseMessage<ContactMessageResponse> save(ContactMessageRequest contactMessageRequest){

        //it is expected to create one message in a day with the same email
        boolean isSameMessageWithSameEmailForToday =
                contactMessageRepository.existsByEmailEqualsAndDateEquals(contactMessageRequest.getEmail(), LocalDate.now());

        if (isSameMessageWithSameEmailForToday){
            throw new ConflictException(Messages.ALREADY_SEND_A_MESSAGE_TODAY);
        }


        ContactMessage contactMessage = createContactMessage(contactMessageRequest);
        ContactMessage savedData = contactMessageRepository.save(contactMessage);
        return ResponseMessage.<ContactMessageResponse>builder()
                // this message should be moved Messages class and called from there
                .message("Contact message created successfully")
                .httpStatus(HttpStatus.CREATED)
                .object(createResponse(savedData))
                .build();

    }

    public Page<ContactMessageResponse> getAll(int page, int size, String sort, String type ){
        // in this solution type property should be instance of Direction
        // method signature -> getAll(int page, int size, String sort, Direction type)
        // Pageable myPageable = PageRequest.of(page, size, Sort.by(type, sort));

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type,"desc")){
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }
        return contactMessageRepository.findAll(pageable).map(this::createResponse);
    }

    public Page<ContactMessageResponse> searchByEmail(String email, int page, int size, String sort, String type){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());

        if (Objects.equals(type,"desc")){
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }


        return contactMessageRepository.findByEmailEquals(email, pageable ).map(this::createResponse);
    }
    public Page<ContactMessageResponse> searchBySubject(String subject, int page, int size, String sort, String type) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if(Objects.equals(type, "desc")){
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }
        return contactMessageRepository.findBySubjectEquals(subject, pageable).map(this::createResponse);
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


    public ResponseMessage deleteMessageById(Long id) {
        isContactMessageExist(id);
        contactMessageRepository.deleteById(id);

        return ResponseMessage.builder()
                .message("Contact Message deleted successfully ")
                .httpStatus(HttpStatus.OK)
                .build();

    }
    private ContactMessage isContactMessageExist(Long id) {

        return contactMessageRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(String.format(Messages.USER_MESSAGE_NOT_FOUND)));

    }

    public ResponseMessage<ContactMessageResponse> updateContactMessage(Long id, ContactMessageRequest contactMessageRequest) {

      ContactMessage contactMessage = isContactMessageExist(id);

      return null;  //complete it



    }
}
