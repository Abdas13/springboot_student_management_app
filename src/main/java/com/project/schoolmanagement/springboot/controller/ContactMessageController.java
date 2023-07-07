package com.project.schoolmanagement.springboot.controller;


import com.project.schoolmanagement.springboot.payload.reponse.ContactMessageResponse;
import com.project.schoolmanagement.springboot.payload.reponse.ResponseMessage;
import com.project.schoolmanagement.springboot.payload.request.ContactMessageRequest;
import com.project.schoolmanagement.springboot.service.ContactMessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("contactMessages")
@RequiredArgsConstructor
public class ContactMessageController {

    private final ContactMessageService contactMessageService;

    @PostMapping("/save")
    public ResponseMessage<ContactMessageResponse> save(@RequestBody @Valid ContactMessageRequest contactMessageRequest){
        //saveContactMessage is better

      return contactMessageService.save(contactMessageRequest);
    }

}
