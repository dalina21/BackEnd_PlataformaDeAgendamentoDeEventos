package com.example.project_events.errors;

public class InvalidEventStatusException extends RuntimeException{
    public InvalidEventStatusException(String message){
        super(message);
    }
}
