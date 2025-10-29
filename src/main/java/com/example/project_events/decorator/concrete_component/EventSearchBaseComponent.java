package com.example.project_events.decorator.concrete_component;

import com.example.project_events.decorator.component.IEventSearchComponent;
import com.example.project_events.enums.StatusEventEnum;
import com.example.project_events.errors.*;
import com.example.project_events.model.Event;
import com.example.project_events.repository.EventRepository;
import com.example.project_events.repository.OrganizerRepository;
import com.example.project_events.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class EventSearchBaseComponent implements IEventSearchComponent {

    private final EventRepository eventRepository;
    private final OrganizerRepository organizerRepository;
    private final ParticipantRepository participantRepository;

    @Override
    public Optional<Event> findEventById(Long idEvent) {
        Optional<Event> event = eventRepository.findById(idEvent);
        if(event.isEmpty()){
            throw new EventNotFoundException("Evento não encontrado!");
        }
        return event;
    }

    @Override
    public List<Event> findAllEventsByOrganizer(UUID uuidOrganizer) {
        List<Event> events = eventRepository.findAllByOrganizerUuid(uuidOrganizer);
        if (!organizerRepository.existsByUuid(uuidOrganizer)){
            throw new UuidNotFoundException("UUID não encontrado");
        }
        if(events.isEmpty()){
            throw new EventNotFoundException("Nenhum evento foi encontrado!");
        }
        return events;
    }

    @Override
    public List<Event> findEventsByOrganizerUuidAndStatus(UUID uuidOrganizer, StatusEventEnum status) {
        List<Event> events = eventRepository.findAllByOrganizerUuidAndStatus(uuidOrganizer, status);
        if (!organizerRepository.existsByUuid(uuidOrganizer)){
            throw new UuidNotFoundException("UUID não encontrado");
        }
        if(events.isEmpty()){
            throw new EventNotFoundException("Nenhum evento foi encontrado!");
        }
        return events;
    }

    @Override
    public List<Event> findAllEvents() {
        List<Event> events = eventRepository.findAll();
        if(events.isEmpty()){
            throw new EventNotFoundException("Nenhum evento encontrado!");
        }
        return events;
    }

    @Override
    public List<Event> findAllEventsAvailableForSubscribe(UUID uuidParticipant) {
        List<Event> events = eventRepository.findAllByStatus(StatusEventEnum.AVAILABLE);

        if(!participantRepository.existsByUuid(uuidParticipant)){
            throw new UserNotFoundException("Usuário participante não encontrado");
        }
        if(events.isEmpty()){
            throw new EventNotFoundException("Nenhum evento disponível encontrado!");
        }
        return events;
    }

    @Override
    public List<Event> findAllOngoingEvents(UUID uuidParticipant) {
        List<Event> events = eventRepository.findAllByStatus(StatusEventEnum.IN_PROGRESS);

        if(!participantRepository.existsByUuid(uuidParticipant)){
            throw new UserNotFoundException("Usuário participante não encontrado");
        }
        if(events.isEmpty()){
            throw new EventNotFoundException("Nenhum evento disponível encontrado!");
        }
        return events;
    }

    @Override
    public List<Event> findAllEventsThatTheParticipantIsSubscribe(UUID uuidParticipant) {
        List<Event> events = eventRepository.findAllByParticipants_Uuid(uuidParticipant);

        if(!participantRepository.existsByUuid(uuidParticipant)){
            throw new UserNotFoundException("Usuário participante não encontrado!");
        }
        if(events.isEmpty()){
            throw new SubscriberNotFoundException("Não foi encontrado nenhuma incrição desse participante em um evento!");
        }
        return events;
    }
}
