package com.example.project_events.dto;

import com.example.project_events.enums.StatusEventEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ResponseEventDTO {
    private long id;
    private String name;
    private String description;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate eventDate;

    private int limitParticipants;
    private int amountOfSubscribers;
    private StatusEventEnum status;
}
