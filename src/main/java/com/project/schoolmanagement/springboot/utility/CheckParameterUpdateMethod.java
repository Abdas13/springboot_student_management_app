package com.project.schoolmanagement.springboot.utility;

import com.project.schoolmanagement.springboot.entity.abstracts.User;
import com.project.schoolmanagement.springboot.payload.request.abstracts.BaseUserRequest;

public class CheckParameterUpdateMethod {

    public static boolean checkUniqueProperties(User user, BaseUserRequest baseUserRequest){

        return user.getSsn().equalsIgnoreCase(baseUserRequest.getSsn())
                || user.getPhoneNumber().equalsIgnoreCase(baseUserRequest.getPhoneNumber())
                || user.getUsername().equalsIgnoreCase(baseUserRequest.getUsername());
    }
}