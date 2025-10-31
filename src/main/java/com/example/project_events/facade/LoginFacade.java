package com.example.project_events.facade;

import com.example.project_events.dto.LoginDTO;
import com.example.project_events.dto.ResponseUserLoginDTO;
import com.example.project_events.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginFacade {

    private final LoginService loginService;

    public ResponseUserLoginDTO loginOrganizer(LoginDTO loginDTO){
        return loginService.loginOrganizer(loginDTO);
    }

    public ResponseUserLoginDTO loginParticipant(LoginDTO loginDTO){
        return loginService.loginParticipant(loginDTO);
    }

}
