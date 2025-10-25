package com.example.project_events.errors;

public class EmailExistingException extends RuntimeException{
    public EmailExistingException(String message){
        super(message);
    }
}
