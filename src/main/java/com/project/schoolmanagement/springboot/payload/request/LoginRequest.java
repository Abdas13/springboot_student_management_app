package com.project.schoolmanagement.springboot.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotNull(message = "Username should not be empty")
    private String username;

    @NotNull(message = "Password should not be empty")
    private String password;



}
