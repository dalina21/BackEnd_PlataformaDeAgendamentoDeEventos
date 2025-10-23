package com.example.project_events.errors;

public class InvalidDateException extends RuntimeException{
    public InvalidDateException(String message){
        super(message);
    }
}
