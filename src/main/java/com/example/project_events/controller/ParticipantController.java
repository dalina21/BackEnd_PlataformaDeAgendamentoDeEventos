package com.example.project_events.controller;

import com.example.project_events.dto.CreateParticipantDTO;
import com.example.project_events.dto.ResponseUserDTO;
import com.example.project_events.dto.UpdateUserDTO;
import com.example.project_events.dto.UpdateUserPasswordDTO;
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
@RequestMapping("/participant")
@RequiredArgsConstructor
public class ParticipantController {

    private final UserFacade userFacade;

    @PostMapping("/register")
    public ResponseEntity<?> createParticipant(@RequestBody @Valid CreateParticipantDTO createParticipantDTO){
        userFacade.createUserParticipant(createParticipantDTO);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Usuário participante cadastrado com sucesso!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{uuid}/update")
    public ResponseEntity<?> updateParticipantInformation(@PathVariable UUID uuid, @RequestBody @Valid UpdateUserDTO updateUserDTO){
        userFacade.updateParticipantInformations(uuid, updateUserDTO);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Participante atualizado com sucesso!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{uuid}/update-password")
    public ResponseEntity<?> updateParticipantPassword(@PathVariable UUID uuid, @RequestBody @Valid UpdateUserPasswordDTO updateUserPasswordDTO){
        userFacade.updateParticipantPassword(uuid, updateUserPasswordDTO);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Senha do participante atualizada com sucesso!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{uuid}/informations")
    public ResponseEntity<?> participantInformation(@PathVariable UUID uuid){
        Map<String, ResponseUserDTO> response = new HashMap<>();
        response.put("organizer", userFacade.participantInformation(uuid));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{uuid}/view/notification-counter")
    public ResponseEntity<?> viewNotificationCounter(@PathVariable UUID uuid){
        Map<String, Integer> response = new HashMap<>();
        response.put("notification counter", userFacade.viewNotificationCounter(uuid));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("{uuid}/reset/notification-counter")
    public ResponseEntity<?> resetNotificationCounter(@PathVariable UUID uuid){
        userFacade.resetNotificationCounter(uuid);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Contador zerado com sucesso!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
