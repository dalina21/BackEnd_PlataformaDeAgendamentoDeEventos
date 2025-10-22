package com.example.project_events.facade;

import com.example.project_events.dto.LoginDTO;
import com.example.project_events.model.Organizer;
import com.example.project_events.model.Participant;
import com.example.project_events.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginFacade {

    private final LoginService loginService;

    public Organizer loginOrganizer(LoginDTO loginDTO){
        return loginService.loginOrganizer(loginDTO);
    }

    public Participant loginParticipant(LoginDTO loginDTO){
        return loginService.loginParticipant(loginDTO);
    }

}
