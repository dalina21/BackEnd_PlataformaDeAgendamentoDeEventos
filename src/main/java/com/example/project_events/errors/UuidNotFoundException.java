package com.example.project_events.errors;

public class UuidNotFoundException extends RuntimeException{
    public  UuidNotFoundException(String message){
        super(message);
    }
}
