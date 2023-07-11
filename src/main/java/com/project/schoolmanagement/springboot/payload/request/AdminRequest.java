package com.project.schoolmanagement.springboot.payload.request;


import com.project.schoolmanagement.springboot.payload.request.abstracts.BaseUserRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AdminRequest extends BaseUserRequest {

    private boolean builtIn;
}
