package com.project.schoolmanagement.springboot.controller;

import com.project.schoolmanagement.springboot.payload.reponse.ResponseMessage;
import com.project.schoolmanagement.springboot.payload.reponse.ViceDeanResponse;
import com.project.schoolmanagement.springboot.payload.request.ViceDeanRequest;
import com.project.schoolmanagement.springboot.service.ViceDeanService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("vicedean")
@RequiredArgsConstructor
public class ViceDeanController {

    private final ViceDeanService viceDeanService;

    @PostMapping("/save")
  //  @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseMessage<ViceDeanResponse> saveViceDean(@RequestBody @Valid ViceDeanRequest viceDeanRequest){
        return viceDeanService.saveViceDean(viceDeanRequest);
    }

    @GetMapping("/getById")
  //  @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<ViceDeanResponse> getViceDeanById(@RequestParam @Valid Long userId){

        return viceDeanService.getViceDeanById(userId);
    }
  //  @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @DeleteMapping("/delete/{userId}")
    public ResponseMessage<?> deleteViceDeanById(@PathVariable Long userId){

        return viceDeanService.deleteViceDeanById(userId);
    }

  //  @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @PutMapping("/update/{viceDeanId}")
    public ResponseMessage<ViceDeanResponse> updateViceDean(@RequestBody @Valid ViceDeanRequest viceDeanRequest,
                                                            @PathVariable Long viceDeanId){
        return viceDeanService.updateViceDean(viceDeanRequest, viceDeanId);
    }

  //  @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @GetMapping("/getAll")
    public List<ViceDeanResponse> getAllViceDeans(){

        return viceDeanService.getAllViceDeans();
    }

    @GetMapping("/search")
  //  @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public Page<ViceDeanResponse> getAllViceDeansByPage(
            @RequestParam(value = "page") int page,
            @RequestParam (value = "size") int size,
            @RequestParam (value = "sort") String sort,
            @RequestParam (value = "type") String type){

        return viceDeanService.getAllDeansByPage(page, size, sort, type);

    }

}
