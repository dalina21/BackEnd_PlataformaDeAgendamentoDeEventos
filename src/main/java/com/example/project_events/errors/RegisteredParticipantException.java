package com.example.project_events.errors;

public class RegisteredParticipantException extends RuntimeException{
    public RegisteredParticipantException(String message){
        super(message);
    }
}
