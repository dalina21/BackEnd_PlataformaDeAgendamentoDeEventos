package com.example.project_events.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserPasswordDTO {
    @NotBlank(message = "O campo da antiga senha é obrigatório")
    @Size(min = 8, message = "O campo da antiga senha deve ter no minimo 8 carcteres")
    private String oldPassword;

    @NotBlank(message = "O campo da nova senha é obrigatório")
    @Size(min = 8, message = "O campo da nova senha deve ter no minimo 8 carcteres")
    private String newPassword;
}
