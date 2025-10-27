package com.example.project_events.errors;

public class EventUnavailableException extends RuntimeException{
    public EventUnavailableException(String message){
        super(message);
    }
}
