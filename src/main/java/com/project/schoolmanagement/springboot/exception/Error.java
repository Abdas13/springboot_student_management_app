package com.project.schoolmanagement.springboot.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Error {

    private String message;

    private int statusCode;



}
