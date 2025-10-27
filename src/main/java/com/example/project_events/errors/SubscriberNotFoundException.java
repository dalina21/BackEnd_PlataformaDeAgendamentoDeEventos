package com.example.project_events.errors;

public class SubscriberNotFoundException extends RuntimeException{
    public SubscriberNotFoundException(String message){
        super(message);
    }
}
