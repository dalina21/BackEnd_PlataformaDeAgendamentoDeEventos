package com.example.project_events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ResponseUserLoginDTO {
    private UUID uuid;
    private String name;
    private String email;
}
