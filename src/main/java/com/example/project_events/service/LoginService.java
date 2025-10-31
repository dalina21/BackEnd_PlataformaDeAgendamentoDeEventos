package com.example.project_events.service;

import com.example.project_events.dto.LoginDTO;
import com.example.project_events.dto.ResponseUserLoginDTO;
import com.example.project_events.errors.InvalidCredentialsException;
import com.example.project_events.model.Organizer;
import com.example.project_events.model.Participant;
import com.example.project_events.repository.OrganizerRepository;
import com.example.project_events.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final OrganizerRepository organizerRepository;
    private final ParticipantRepository participantRepository;
    private final BCryptPasswordEncoder encoder;

    public ResponseUserLoginDTO loginOrganizer(LoginDTO loginDTO){
        Optional<Organizer> organizer = organizerRepository.findByEmail(loginDTO.getEmail());
        if(organizer.isEmpty() || !encoder.matches(loginDTO.getPassword(), organizer.get().getPassword())){
            throw new InvalidCredentialsException("Credenciais Inválidas!");
        }
        return new ResponseUserLoginDTO(
                organizer.get().getUuid(),
                organizer.get().getName(),
                organizer.get().getEmail()
        );
    }

    public ResponseUserLoginDTO loginParticipant(LoginDTO loginDTO){
        Optional<Participant> participant = participantRepository.findByEmail(loginDTO.getEmail());
        if(participant.isEmpty() || !encoder.matches(loginDTO.getPassword(), participant.get().getPassword())){
            throw new InvalidCredentialsException("Credenciais Inválidas!");
        }
        return new ResponseUserLoginDTO(
                participant.get().getUuid(),
                participant.get().getName(),
                participant.get().getEmail()
        );
    }
}
