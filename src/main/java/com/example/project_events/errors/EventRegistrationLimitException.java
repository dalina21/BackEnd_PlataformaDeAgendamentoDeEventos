package com.example.project_events.errors;

public class EventRegistrationLimitException extends RuntimeException{
    public EventRegistrationLimitException(String message){
        super(message);
    }
}
