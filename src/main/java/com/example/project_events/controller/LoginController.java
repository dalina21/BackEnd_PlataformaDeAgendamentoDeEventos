package com.example.project_events.controller;

import com.example.project_events.dto.LoginDTO;
import com.example.project_events.dto.ResponseUserLoginDTO;
import com.example.project_events.facade.LoginFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginFacade loginFacade;

    @PostMapping("/organizer")
    public ResponseEntity<?> loginOrganizer(@RequestBody @Valid LoginDTO loginDTO){
        Map<String, ResponseUserLoginDTO> response = new HashMap<>();
        response.put("organizer", loginFacade.loginOrganizer(loginDTO));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/participant")
    public ResponseEntity<?> loginParticipant(@RequestBody @Valid LoginDTO loginDTO){
        Map<String, ResponseUserLoginDTO> response = new HashMap<>();
        response.put("participant", loginFacade.loginParticipant(loginDTO));
        return ResponseEntity.ok(response);
    }
}
