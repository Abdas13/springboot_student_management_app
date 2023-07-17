package com.project.schoolmanagement.springboot.payload.reponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse {

    private String username;
    private String ssn;
    private String role;
    private String token;
    private String name;
    private String isAdvisory;

}
