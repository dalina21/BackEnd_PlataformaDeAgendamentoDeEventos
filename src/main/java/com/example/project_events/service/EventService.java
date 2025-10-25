package com.example.project_events.service;

import com.example.project_events.dto.RegisterEventDTO;
import com.example.project_events.dto.ResponseEventDTO;
import com.example.project_events.enums.StatusEventEnum;
import com.example.project_events.errors.EventNotFoundException;
import com.example.project_events.errors.InvalidDateException;
import com.example.project_events.errors.UnauthorizedException;
import com.example.project_events.errors.UuidNotFoundException;
import com.example.project_events.model.Event;
import com.example.project_events.model.Organizer;
import com.example.project_events.repository.EventRepository;
import com.example.project_events.repository.OrganizerRepository;
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

    public void registerEvent(UUID uuid, RegisterEventDTO registerEventDTO){
        Optional<Organizer> organizer = organizerRepository.findByUuid(uuid);

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
    }

    public void updateEvent(UUID uuid, long idEvent, RegisterEventDTO registerEventDTO){
        Optional<Event> event = eventRepository.findById(idEvent);

        if (!organizerRepository.existsByUuid(uuid)){
            throw new UuidNotFoundException("UUID não encontrado");
        }
        if(event.isEmpty()){
            throw new EventNotFoundException("Evento não encontrado!");
        }
        if(!event.get().getOrganizer().getUuid().equals(uuid)){
            throw new UnauthorizedException("Este organizador não tem permissão para editar esse evento!");
        }
        if(registerEventDTO.getEventDate().isBefore(LocalDate.now())){
            throw new InvalidDateException("Datas no passado não são permitidas para criar um evento!");
        }

        event.get().setName(registerEventDTO.getName());
        event.get().setDescription(registerEventDTO.getDescription());
        event.get().setEventDate(registerEventDTO.getEventDate());
        event.get().setLimitParticipants(registerEventDTO.getLimitParticipants());
        eventRepository.save(event.get());
    }

    public void deleteEvent(UUID uuid, long idEvent){
        Optional<Event> event = eventRepository.findById(idEvent);

        if (!organizerRepository.existsByUuid(uuid)){
            throw new UuidNotFoundException("UUID não encontrado");
        }
        if(event.isEmpty()){
            throw new EventNotFoundException("Evento não encontrado!");
        }
        if(!event.get().getOrganizer().getUuid().equals(uuid)){
            throw new UnauthorizedException("Este organizador não tem permissão para deletar esse evento!");
        }

        eventRepository.delete(event.get());
    }

    public ResponseEventDTO findEventById(UUID uuid, Long idEvent){
        Optional<Event> event = eventRepository.findById(idEvent);
        if (!organizerRepository.existsByUuid(uuid)){
            throw new UuidNotFoundException("UUID não encontrado");
        }
        if(event.isEmpty()){
            throw new EventNotFoundException("Evento não encontrado!");
        }
        if(!event.get().getOrganizer().getUuid().equals(uuid)){
            throw new UnauthorizedException("Este organizador não tem permissão para visualizar esse evento!");
        }
        return new ResponseEventDTO(
                event.get().getName(),
                event.get().getDescription(),
                event.get().getEventDate(),
                event.get().getLimitParticipants(),
                event.get().getAmountOfSubscribers(),
                event.get().getStatus()
        );
    }

    public List<ResponseEventDTO> findAllEventsByOrganizer(UUID uuid){
        List<Event> events = eventRepository.findAllByOrganizerUuid(uuid);
        if (!organizerRepository.existsByUuid(uuid)){
            throw new UuidNotFoundException("UUID não encontrado");
        }
        if(events.isEmpty()){
            throw new EventNotFoundException("Nenhum evento foi encontrado!");
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

    public List<ResponseEventDTO> findEventsByOrganizerUuidAndStatus(UUID uuid, StatusEventEnum status){
        List<Event> events = eventRepository.findAllByOrganizerUuidAndStatus(uuid, status);
        if (!organizerRepository.existsByUuid(uuid)){
            throw new UuidNotFoundException("UUID não encontrado");
        }
        if(events.isEmpty()){
            throw new EventNotFoundException("Nenhum evento foi encontrado!");
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

    public List<ResponseEventDTO> findAllEvents(){
        List<Event> events = eventRepository.findAll();
        if(events.isEmpty()){
            throw new EventNotFoundException("Nenhum evento encontrado!");
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
