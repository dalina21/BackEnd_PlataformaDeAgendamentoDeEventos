package com.example.project_events.service;

import com.example.project_events.dto.CreateParticipantDTO;
import com.example.project_events.dto.ResponseUserDTO;
import com.example.project_events.dto.UpdateUserDTO;
import com.example.project_events.errors.EmailExistingException;
import com.example.project_events.errors.InvalidCredentialsException;
import com.example.project_events.errors.UserNotFoundException;
import com.example.project_events.model.Participant;
import com.example.project_events.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final BCryptPasswordEncoder encoder;

    public void createParticipant(CreateParticipantDTO createParticipantDTO){
        if(participantRepository.existsByEmail(createParticipantDTO.getEmail())){
            throw new EmailExistingException("Já existe um participante cadastrado com esse email!");
        }

        Participant participant = new Participant();
        participant.setName(createParticipantDTO.getName());
        participant.setPassword(encoder.encode(createParticipantDTO.getPassword()));
        participant.setEmail(createParticipantDTO.getEmail());
        participantRepository.save(participant);
    }

    public void updateParticipantInformations(UUID uuid, UpdateUserDTO updateUserDTO){
        Optional<Participant> participant = participantRepository.findByUuid(uuid);

        if(participant.isEmpty()){
            throw new UserNotFoundException("Usuário participante não encontrado!");
        }
        if(participantRepository.existsByEmail(updateUserDTO.getEmail())){
            throw new EmailExistingException("Já existe um participante cadastrado com esse email!");
        }
        if(!encoder.matches(updateUserDTO.getOldPassword(), participant.get().getPassword())){
            throw new InvalidCredentialsException("Senha atual incorreta!");
        }

        participant.get().setName(updateUserDTO.getName());
        participant.get().setEmail(updateUserDTO.getEmail());
        participant.get().setPassword(encoder.encode(updateUserDTO.getNewPassword()));
        participantRepository.save(participant.get());
    }

    public ResponseUserDTO participantInformation(UUID uuid){
        Optional<Participant> participant = participantRepository.findByUuid(uuid);
        if(participant.isEmpty()){
            throw new UserNotFoundException("Usuário organizador não encontrado!");
        }
        return new ResponseUserDTO(
                participant.get().getName(),
                participant.get().getEmail(),
                participant.get().getPassword()
        );
    }
}
