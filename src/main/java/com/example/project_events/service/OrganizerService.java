package com.example.project_events.service;

import com.example.project_events.dto.ResponseUserDTO;
import com.example.project_events.dto.ResponseUserLoginDTO;
import com.example.project_events.dto.UpdateUserDTO;
import com.example.project_events.dto.UpdateUserPasswordDTO;
import com.example.project_events.errors.EmailExistingException;
import com.example.project_events.errors.InvalidCredentialsException;
import com.example.project_events.errors.UserNotFoundException;
import com.example.project_events.model.Organizer;
import com.example.project_events.repository.OrganizerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizerService {

    private final OrganizerRepository organizerRepository;
    private final BCryptPasswordEncoder encoder;

    public void updateOrganizerInformations(UUID uuid, UpdateUserDTO updateUserDTO){
        Optional<Organizer> organizer = organizerRepository.findByUuid(uuid);

        if(organizer.isEmpty()){
            throw new UserNotFoundException("Usuário organizador não encontrado!");
        }
        if(organizerRepository.existsByEmailAndUuidNot(updateUserDTO.getEmail(), uuid)){
            throw new EmailExistingException("Já existe um organizador cadastrado com esse email!");
        }

        organizer.get().setName(updateUserDTO.getName());
        organizer.get().setEmail(updateUserDTO.getEmail());
        organizerRepository.save(organizer.get());
    }

    public void updateOrganizerPassword(UUID uuid, UpdateUserPasswordDTO updateUserPasswordDTO){
        Optional<Organizer> organizer = organizerRepository.findByUuid(uuid);

        if(organizer.isEmpty()){
            throw new UserNotFoundException("Usuário organizador não encontrado!");
        }
        if(!encoder.matches(updateUserPasswordDTO.getOldPassword(), organizer.get().getPassword())){
            throw new InvalidCredentialsException("Senha atual incorreta!");
        }

        organizer.get().setPassword(encoder.encode(updateUserPasswordDTO.getNewPassword()));
        organizerRepository.save(organizer.get());
    }

    public ResponseUserDTO organizerInformation(UUID uuid){
        Optional<Organizer> organizer = organizerRepository.findByUuid(uuid);
        if(organizer.isEmpty()){
            throw new UserNotFoundException("Usuário organizador não encontrado!");
        }
        return new ResponseUserDTO(
                organizer.get().getName(),
                organizer.get().getEmail()
        );
    }
}
