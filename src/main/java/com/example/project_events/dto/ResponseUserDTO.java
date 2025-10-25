package com.example.project_events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseUserDTO {
    private String name;
    private String email;
    private String password;
}
