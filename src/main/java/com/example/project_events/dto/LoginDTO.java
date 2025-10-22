package com.example.project_events.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginDTO {

    @NotBlank(message = "O campo do email é obtigatório!")
    @Email(message = "Formato de email inválido!")
    private String email;

    @NotBlank(message = "O campo da senha é obtigatório!")
    @Size(min = 8, message = "O campo da senha precisa ter no minimo 8 caracteres!")
    private String password;
}
