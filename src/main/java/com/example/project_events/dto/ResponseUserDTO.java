package com.example.project_events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ResponseUserDTO {
    private UUID uuid;
    private String name;
    private String email;
}
