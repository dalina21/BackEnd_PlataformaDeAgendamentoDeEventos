package com.example.project_events.service;

import com.example.project_events.dto.CreateParticipantDTO;
import com.example.project_events.dto.ResponseEventDTO;
import com.example.project_events.dto.ResponseUserDTO;
import com.example.project_events.dto.UpdateUserDTO;
import com.example.project_events.errors.*;
import com.example.project_events.model.Event;
import com.example.project_events.model.Participant;
import com.example.project_events.repository.EventRepository;
import com.example.project_events.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final EventRepository eventRepository;
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
            throw new UserNotFoundException("Usuário participante não encontrado!");
        }
        return new ResponseUserDTO(
                participant.get().getName(),
                participant.get().getEmail()
        );
    }

    public void subscribeForAnEvent(UUID uuidParticipant, Long idEvent){
        Optional<Event> event = eventRepository.findById(idEvent);
        Optional<Participant> participant = participantRepository.findByUuid(uuidParticipant);

        if(participant.isEmpty()){
            throw new UserNotFoundException("Usuário participante não encontrado");
        }
        if(event.isEmpty()){
            throw new EventNotFoundException("Evento não encontrado!");
        }
        if(event.get().getLimitParticipants() == event.get().getAmountOfSubscribers()){
            throw new EventRegistrationLimitException("Esse evento já atingiu o número máximo de participantes");
        }

        event.get().setAmountOfSubscribers(event.get().getAmountOfSubscribers() + 1);
        event.get().getParticipants().add(participant.get());
        participant.get().getEvents().add(event.get());
        participant.get().getPosts().addAll(event.get().getPosts());
        eventRepository.save(event.get());
        participantRepository.save(participant.get());
    }

    public void cancelSubscribeForAnEvent(UUID uuidParticipant, Long idEvent){
        Optional<Event> event = eventRepository.findById(idEvent);
        Optional<Participant> participant = participantRepository.findByUuid(uuidParticipant);

        if(participant.isEmpty()){
            throw new UserNotFoundException("Usuário participante não encontrado");
        }
        if(event.isEmpty()){
            throw new EventNotFoundException("Evento não encontrado!");
        }

        event.get().setAmountOfSubscribers(event.get().getAmountOfSubscribers() - 1);
        event.get().getParticipants().remove(participant.get());
        participant.get().getEvents().remove(event.get());
        participant.get().getPosts().removeAll(event.get().getPosts());
        eventRepository.save(event.get());
        participantRepository.save(participant.get());
    }

    public List<ResponseEventDTO> findAllEventsThatTheParticipantIsSubscribe(UUID uuidParticipant){
        List<Event> events = eventRepository.findAllByParticipants_Uuid(uuidParticipant);

        if(participantRepository.existsByUuid(uuidParticipant)){
            throw new UserNotFoundException("Usuário participante não encontrado!");
        }
        if(events.isEmpty()){
            throw new SubscriberNotFoundException("Não foi encontrado nenhuma incrição desse participante em um evento!");
        }

        return events.stream()
                .map(e -> new ResponseEventDTO(
                        e.getName(),
                        e.getDescription(),
                        e.getEventDate(),
                        e.getLimitParticipants(),
                        e.getAmountOfSubscribers(),
                        e.getStatus()
                )).toList();
    }
}
