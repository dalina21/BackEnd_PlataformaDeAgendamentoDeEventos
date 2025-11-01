package com.example.project_events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ResponsePostDTO {
    private long idPost;
    private String message;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate postingDate;

    private String nameOrganizer;
}
