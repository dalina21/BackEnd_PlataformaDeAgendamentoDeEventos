package com.example.project_events.service;

import com.example.project_events.decorator.component.IEventSearchComponent;
import com.example.project_events.dto.RegisterEventDTO;
import com.example.project_events.dto.ResponseEventDTO;
import com.example.project_events.dto.ResponseUserDTO;
import com.example.project_events.enums.StatusEventEnum;
import com.example.project_events.errors.*;
import com.example.project_events.model.Event;
import com.example.project_events.model.Organizer;
import com.example.project_events.model.Participant;
import com.example.project_events.repository.EventRepository;
import com.example.project_events.repository.OrganizerRepository;
import com.example.project_events.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final OrganizerRepository organizerRepository;
    private final ParticipantRepository participantRepository;
    private final IEventSearchComponent eventSearchComponent;

    public void registerEvent(UUID uuidOrganizer, RegisterEventDTO registerEventDTO){
        Optional<Organizer> organizer = organizerRepository.findByUuid(uuidOrganizer);

        if (organizer.isEmpty()){
            throw new UuidNotFoundException("Uuid do organizador não encontrado!");
        }
        if(registerEventDTO.getEventDate().isBefore(LocalDate.now())){
            throw new InvalidDateException("Datas no passado não são permitidas para criar um evento!");
        }

        Event newEvent = new Event();
        newEvent.setName(registerEventDTO.getName());
        newEvent.setDescription(registerEventDTO.getDescription());
        newEvent.setEventDate(registerEventDTO.getEventDate());
        newEvent.setLimitParticipants(registerEventDTO.getLimitParticipants());
        newEvent.setStatus(StatusEventEnum.AVAILABLE);
        newEvent.setOrganizer(organizer.get());
        eventRepository.save(newEvent);

        organizer.get().getEvents().add(newEvent);
        organizerRepository.save(organizer.get());
    }

    public void updateEvent(UUID uuidOrganizer, long idEvent, RegisterEventDTO registerEventDTO){

        Optional<Event> event = eventRepository.findById(idEvent);

        if (!organizerRepository.existsByUuid(uuidOrganizer)){
            throw new UuidNotFoundException("UUID não encontrado");
        }
        if(event.isEmpty()){
            throw new EventNotFoundException("Evento não encontrado!");
        }
        if(!event.get().getOrganizer().getUuid().equals(uuidOrganizer)){
            throw new UnauthorizedException("Este organizador não tem permissão para editar esse evento!");
        }
        if(registerEventDTO.getEventDate().isBefore(LocalDate.now())){
            throw new InvalidDateException("Datas no passado não são permitidas para editar um evento!");
        }

        event.get().setName(registerEventDTO.getName());
        event.get().setDescription(registerEventDTO.getDescription());
        event.get().setEventDate(registerEventDTO.getEventDate());
        event.get().setLimitParticipants(registerEventDTO.getLimitParticipants());
        eventRepository.save(event.get());
    }

    public void deleteEvent(UUID uuidOrganizer, long idEvent){
        Optional<Event> event = eventRepository.findById(idEvent);

        if (!organizerRepository.existsByUuid(uuidOrganizer)){
            throw new UuidNotFoundException("UUID não encontrado");
        }
        if(event.isEmpty()){
            throw new EventNotFoundException("Evento não encontrado!");
        }
        if(!event.get().getOrganizer().getUuid().equals(uuidOrganizer)){
            throw new UnauthorizedException("Este organizador não tem permissão para deletar esse evento!");
        }

        eventRepository.delete(event.get());
    }

    public ResponseEventDTO findEventById(UUID uuidOrganizer, Long idEvent){
        Optional<Event> event = eventSearchComponent.findEventById(idEvent);
        if(event.isEmpty()){
            throw new EventNotFoundException("Evento não encontrado!");
        }
        return new ResponseEventDTO(
                event.get().getId(),
                event.get().getName(),
                event.get().getDescription(),
                event.get().getEventDate(),
                event.get().getLimitParticipants(),
                event.get().getAmountOfSubscribers(),
                event.get().getStatus()
        );
    }

    public List<ResponseEventDTO> findAllEventsByOrganizer(UUID uuidOrganizer){
        List<Event> events = eventSearchComponent.findAllEventsByOrganizer(uuidOrganizer);
        return events.stream()
                .map(e -> new ResponseEventDTO(
                        e.getId(),
                        e.getName(),
                        e.getDescription(),
                        e.getEventDate(),
                        e.getLimitParticipants(),
                        e.getAmountOfSubscribers(),
                        e.getStatus()
                )).toList();
    }

    public List<ResponseEventDTO> findEventsByOrganizerUuidAndStatus(UUID uuidOrganizer, StatusEventEnum status){
        List<Event> events = eventSearchComponent.findEventsByOrganizerUuidAndStatus(uuidOrganizer, status);
        return events.stream()
                .map(e -> new ResponseEventDTO(
                        e.getId(),
                        e.getName(),
                        e.getDescription(),
                        e.getEventDate(),
                        e.getLimitParticipants(),
                        e.getAmountOfSubscribers(),
                        e.getStatus()
                )).toList();
    }

    public List<ResponseEventDTO> findAllEvents(){
        List<Event> events = eventSearchComponent.findAllEvents();
        return events.stream()
                .map(e -> new ResponseEventDTO(
                        e.getId(),
                        e.getName(),
                        e.getDescription(),
                        e.getEventDate(),
                        e.getLimitParticipants(),
                        e.getAmountOfSubscribers(),
                        e.getStatus()
                )).toList();
    }

    public void subscribeForAnEvent(UUID uuidParticipant, Long idEvent){
        Optional<Event> event = eventSearchComponent.findEventById(idEvent);
        Optional<Participant> participant = participantRepository.findByUuid(uuidParticipant);

        if(participant.isEmpty()){
            throw new UserNotFoundException("Usuário participante não encontrado");
        }
        if(event.isEmpty()){
            throw new EventNotFoundException("Evento não encontrado!");
        }
        if(!event.get().getStatus().equals(StatusEventEnum.AVAILABLE)){
            throw new EventUnavailableException("Este evento não se encontra disponível para a inscrição!");
        }
        if(event.get().getParticipants().contains(participant.get())){
            throw new RegisteredParticipantException("Este participante já se encontra inscrito nesse evento!");
        }

        event.get().setAmountOfSubscribers(event.get().getAmountOfSubscribers() + 1);
        event.get().getParticipants().add(participant.get());
        participant.get().getEvents().add(event.get());
        participant.get().getPosts().addAll(event.get().getPosts());
        eventRepository.save(event.get());
        participantRepository.save(participant.get());
    }

    public void cancelSubscribeForAnEvent(UUID uuidParticipant, Long idEvent){
        Optional<Event> event = eventSearchComponent.findEventById(idEvent);
        Optional<Participant> participant = participantRepository.findByUuid(uuidParticipant);

        if(participant.isEmpty()){
            throw new UserNotFoundException("Usuário participante não encontrado");
        }
        if(event.isEmpty()){
            throw new EventNotFoundException("Evento não encontrado!");
        }
        if(!event.get().getParticipants().contains(participant.get())){
            throw new SubscriberNotFoundException("Não foi encontrada nenhuma inscrição desse participante nesse evento!");
        }

        event.get().setAmountOfSubscribers(event.get().getAmountOfSubscribers() - 1);
        event.get().getParticipants().remove(participant.get());
        participant.get().getEvents().remove(event.get());
        participant.get().getPosts().removeAll(event.get().getPosts());
        eventRepository.save(event.get());
        participantRepository.save(participant.get());
    }

    public List<ResponseEventDTO> findAllEventsAvailableForSubscribe(UUID uuidParticipant){
        List<Event> events = eventSearchComponent.findAllEventsAvailableForSubscribe(uuidParticipant);
        return events.stream()
                .map(e -> new ResponseEventDTO(
                        e.getId(),
                        e.getName(),
                        e.getDescription(),
                        e.getEventDate(),
                        e.getLimitParticipants(),
                        e.getAmountOfSubscribers(),
                        e.getStatus()
                )).toList();
    }

    public List<ResponseEventDTO> findAllOngoingEvents(UUID uuidParticipant){
        List<Event> events = eventSearchComponent.findAllOngoingEvents(uuidParticipant);
        return events.stream()
                .map(e -> new ResponseEventDTO(
                        e.getId(),
                        e.getName(),
                        e.getDescription(),
                        e.getEventDate(),
                        e.getLimitParticipants(),
                        e.getAmountOfSubscribers(),
                        e.getStatus()
                )).toList();
    }

    public List<ResponseEventDTO> findAllEventsThatTheParticipantIsSubscribe(UUID uuidParticipant){
        List<Event> events = eventSearchComponent.findAllEventsThatTheParticipantIsSubscribe(uuidParticipant);
        return events.stream()
                .map(e -> new ResponseEventDTO(
                        e.getId(),
                        e.getName(),
                        e.getDescription(),
                        e.getEventDate(),
                        e.getLimitParticipants(),
                        e.getAmountOfSubscribers(),
                        e.getStatus()
                )).toList();
    }

    public List<ResponseUserDTO> listParticipantsOfAnEvent(UUID uuidOrganizer, Long idEvent){
        Optional<Event> event = eventRepository.findById(idEvent);

        if(!organizerRepository.existsByUuid(uuidOrganizer)){
            throw new UserNotFoundException("Usuario organizador não encontrado!");
        }
        if(event.isEmpty()){
            throw new EventNotFoundException("Evento não encontrado!");
        }
        if(!event.get().getOrganizer().getUuid().equals(uuidOrganizer)){
            throw new UnauthorizedException("Este organizador não tem permissão para visualizar os participantes desse evento");
        }
        if(event.get().getParticipants().isEmpty()){
            throw new UserNotFoundException("Nenhum participante se inscreveu nesse evento!");
        }

        return event.get().getParticipants().stream()
                .map(p -> new ResponseUserDTO(
                        p.getUuid(),
                        p.getName(),
                        p.getEmail()
                )).toList();
    }
}
