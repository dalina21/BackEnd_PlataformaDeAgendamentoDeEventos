package com.example.project_events.controller;

import com.example.project_events.dto.ResponseUserLoginDTO;
import com.example.project_events.dto.UpdateUserDTO;
import com.example.project_events.facade.UserFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/organizer")
@RequiredArgsConstructor
public class OrganizerController {

    private final UserFacade userFacade;

    @GetMapping("/{uuid}/informations")
    public ResponseEntity<?> organizerInformation(@PathVariable UUID uuid){
        Map<String, ResponseUserLoginDTO> response = new HashMap<>();
        response.put("organizer", userFacade.organizerInformation(uuid));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{uuid}/update")
    public ResponseEntity<?> updateOrganizerInformations(@PathVariable UUID uuid, @RequestBody @Valid UpdateUserDTO updateUserDTO){
        userFacade.updateOrganizerInformations(uuid, updateUserDTO);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Organizador atualizado com sucesso!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
