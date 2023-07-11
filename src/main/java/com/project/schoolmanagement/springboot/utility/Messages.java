package com.project.schoolmanagement.springboot.utility;

public class Messages {

    private Messages(){

    }
    public static final String ALREADY_SEND_A_MESSAGE_TODAY = "Error: You have already sent this message today";

    public static final String USER_MESSAGE_NOT_FOUND = "Error: User not found with this id";

    public static final String ALREADY_REGISTER_MESSAGE_USERNAME = "Error: User with username %s is already registered.";

    public static final String ALREADY_REGISTER_MESSAGE_SSN = "Error: User with ssn %s is already registered.";

    public static final String ALREADY_REGISTER_MESSAGE_PHONE_NUMBER = "Error: User with phone number %s is already registered.";

    public static final String ROLE_NOT_FOUND = "There is no role like that, check the database";
}

