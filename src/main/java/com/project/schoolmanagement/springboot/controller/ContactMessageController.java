package com.project.schoolmanagement.springboot.controller;


import com.project.schoolmanagement.springboot.payload.reponse.ContactMessageResponse;
import com.project.schoolmanagement.springboot.payload.reponse.ResponseMessage;
import com.project.schoolmanagement.springboot.payload.request.ContactMessageRequest;
import com.project.schoolmanagement.springboot.service.ContactMessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("contactMessages")
@RequiredArgsConstructor
public class ContactMessageController {

    private final ContactMessageService contactMessageService;

    @PostMapping("/save")
    public ResponseMessage<ContactMessageResponse> save(@RequestBody @Valid ContactMessageRequest contactMessageRequest) {
        //saveContactMessage is better

        return contactMessageService.save(contactMessageRequest);
    }
    @GetMapping("/getAll")
    public Page<ContactMessageResponse> getAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "date") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type) {

        return contactMessageService.getAll(page, size, sort, type);

    }
    @GetMapping("/searchByEmail")
    public Page<ContactMessageResponse> searchByEmail(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "date") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type) {

        return contactMessageService.searchByEmail(email, page, size, sort, type);
    }
    @GetMapping("/searchBySubject")
    public Page<ContactMessageResponse> searchBySubject(
            @RequestParam(value = "subject") String subject,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "date") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type) {

        return contactMessageService.searchBySubject(subject, page, size, sort, type);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseMessage deleteMessageById(@PathVariable Long id){
        return contactMessageService.deleteMessageById(id);
    }

    @PutMapping
    public ResponseMessage<ContactMessageResponse> updateMessageById(@PathVariable Long id,
                                                                     @RequestBody @Valid ContactMessageRequest contactMessageRequest){
        return contactMessageService.updateContactMessage(id, contactMessageRequest);
    }

    //TODO
    // 1- DELETE by ID,  Done
    // 2- UPDATE,
    // 3- GET All messages as a list
}