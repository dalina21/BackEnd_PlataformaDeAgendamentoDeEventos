package com.example.project_events.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateEventDTO {
    @NotBlank(message = "O campo do nome é obrigatório!")
    private String name;

    @NotBlank(message = "O campo da descrição do evento é obrigatório!")
    @Size(min = 20, message = "O campo da descrição precisa ter no minimo 20 caracteres")
    private String description;
}
