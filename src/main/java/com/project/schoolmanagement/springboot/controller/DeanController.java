package com.project.schoolmanagement.springboot.controller;

import com.project.schoolmanagement.springboot.payload.reponse.DeanResponse;
import com.project.schoolmanagement.springboot.payload.reponse.ResponseMessage;
import com.project.schoolmanagement.springboot.payload.request.DeanRequest;
import com.project.schoolmanagement.springboot.service.DeanService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("dean")
@RequiredArgsConstructor
//@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")   it can be added to class level
public class DeanController {

    private final DeanService deanService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseMessage<DeanResponse> save(@RequestBody @Valid DeanRequest deanRequest){

        return deanService.save(deanRequest);

    }
    @PutMapping("/update/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<DeanResponse> update(@RequestBody @Valid DeanRequest deanRequest,
                                                @PathVariable Long userId){

        return deanService.update(deanRequest, userId);
    }

    @DeleteMapping("/delete/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<?> deleteDeanById(@PathVariable Long userId){

        return deanService.deleteDeanById(userId);
    }
    @GetMapping("/getManagerById/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<DeanResponse> getDeanById(@PathVariable Long userId){
        return deanService.getDeanById(userId);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public List<DeanResponse> getAllDeans(){
        return deanService.getAllDeans();
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public Page<DeanResponse> getAllDeansByPage(
            @RequestParam (value = "page") int page,
            @RequestParam (value = "size") int size,
            @RequestParam (value = "sort") String sort,
            @RequestParam (value = "type") String type){

        return deanService.getAllDeansByPage(page, size, sort, type);

    }
}
