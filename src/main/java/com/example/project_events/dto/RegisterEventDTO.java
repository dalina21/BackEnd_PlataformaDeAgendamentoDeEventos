package com.example.project_events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class RegisterEventDTO {

    @NotBlank(message = "O campo do nome é obrigatório!")
    private String name;

    @NotBlank(message = "O campo da descrição do evento é obrigatório!")
    @Size(min = 20, message = "O campo da descrição precisa ter no minimo 20 caracteres")
    private String description;

    @NotNull(message = "O campo da data do evento é obrigatório!")
    private LocalDate eventDate;

    @NotNull(message = "O campo do limite de participantes é obrigatório!")
    @Min(value = 1, message = "O campo limite de participantes precisa ser maior que 0")
    private int limitParticipants;
}
