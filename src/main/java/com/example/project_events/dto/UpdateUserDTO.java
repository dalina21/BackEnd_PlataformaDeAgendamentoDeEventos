package com.example.project_events.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserDTO {

    @NotBlank(message = "O campo do nome é obrigatório!")
    private String name;

    @Email(message = "Formato de email inválido!")
    @NotBlank(message = "O campo do email é obrigatório!")
    private String email;
}
