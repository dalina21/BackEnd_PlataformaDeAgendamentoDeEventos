package com.example.project_events.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreatePostDTO {

    @NotBlank(message = "O campo da menssagem da postagem é obrigatório!")
    @Size(min = 20, message = "A menssagem da postagem deve ter no minimo 20 caracteres!")
    private String message;
}
