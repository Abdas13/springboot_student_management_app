package com.project.schoolmanagement.springboot.controller;

import com.project.schoolmanagement.springboot.payload.reponse.DeanResponse;
import com.project.schoolmanagement.springboot.payload.reponse.ResponseMessage;
import com.project.schoolmanagement.springboot.payload.request.DeanRequest;
import com.project.schoolmanagement.springboot.service.DeanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("dean")
@RequiredArgsConstructor
public class DeanController {

    private final DeanService deanService;

    @PostMapping("/save")
    public ResponseMessage<DeanResponse> save(@RequestBody @Valid DeanRequest deanRequest){

        return deanService.save(deanRequest);

    }
    public ResponseMessage<DeanResponse> update(@RequestBody @Valid DeanRequest deanRequest,
                                                @PathVariable Long userId){

        return deanService.update(deanRequest, userId);
    }

    public ResponseMessage<?> deleteDeanById(@PathVariable Long userId){

        return deanService.deleteDeanById(userId);
    }
}
