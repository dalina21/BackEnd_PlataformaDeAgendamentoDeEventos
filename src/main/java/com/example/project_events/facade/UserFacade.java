package com.example.project_events.facade;

import com.example.project_events.dto.CreateParticipantDTO;
import com.example.project_events.dto.ResponseUserDTO;
import com.example.project_events.dto.ResponseUserLoginDTO;
import com.example.project_events.dto.UpdateUserDTO;
import com.example.project_events.service.OrganizerService;
import com.example.project_events.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final OrganizerService organizerService;
    private final ParticipantService participantService;

    public void updateOrganizerInformations(UUID uuid, UpdateUserDTO updateUserDTO){
        organizerService.updateOrganizerInformations(uuid, updateUserDTO);
    }

    public ResponseUserDTO organizerInformation(UUID uuid){
        return organizerService.organizerInformation(uuid);
    }

    public void updateParticipantInformations(UUID uuid, UpdateUserDTO updateUserDTO){
        participantService.updateParticipantInformations(uuid, updateUserDTO);
    }

    public void createUserParticipant(CreateParticipantDTO createParticipantDTO){
        participantService.createParticipant(createParticipantDTO);
    }

    public int viewNotificationCounter(UUID uuidParticipant){
        return participantService.viewNotificationCounter(uuidParticipant);
    }

    public void resetNotificationCounter(UUID uuidParticipant){
        participantService.resetNotificationCounter(uuidParticipant);
    }

    public ResponseUserDTO participantInformation(UUID uuid){
        return participantService.participantInformation(uuid);
    }
}

