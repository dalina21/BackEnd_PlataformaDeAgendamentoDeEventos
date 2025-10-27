package com.example.project_events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ResponsePostDTO {
    private String message;
    private LocalDate postingDate;
    private String nameOrganizer;
}
