package com.example.project_events.decorator.concrete_decorators;

import com.example.project_events.decorator.component.IEventSearchComponent;
import com.example.project_events.decorator.abstract_decorator.EventSearchDecorator;
import com.example.project_events.enums.StatusEventEnum;
import com.example.project_events.errors.EventNotFoundException;
import com.example.project_events.model.Event;
import com.example.project_events.repository.EventRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class StatusUpdateDecorator extends EventSearchDecorator {

    private final EventRepository eventRepository;
    public StatusUpdateDecorator(IEventSearchComponent decoratedSearch, EventRepository eventRepository) {
        super(decoratedSearch);
        this.eventRepository = eventRepository;
    }

    private void updateEventStatus(Long idEvent){
        Optional<Event> event = eventRepository.findById(idEvent);
        if(event.isEmpty()){
            throw new EventNotFoundException("Evento não encontrado!");
        }
        if(event.get().getLimitParticipants() == event.get().getAmountOfSubscribers()){
            event.get().setStatus(StatusEventEnum.UNAVAILABLE);
        } else if(LocalDate.now().equals(event.get().getEventDate())){
            event.get().setStatus(StatusEventEnum.IN_PROGRESS);
        } else if(event.get().getEventDate().isBefore(LocalDate.now())){
            event.get().setStatus(StatusEventEnum.COMPLETED);
        } else {
            event.get().setStatus(StatusEventEnum.AVAILABLE);
        }
        eventRepository.save(event.get());
    }

    private List<Event> applyStatusUpdateToEvents(List<Event> events){
        for(Event e : events){
            updateEventStatus(e.getId());
        }
        return events;
    }

    @Override
    public Optional<Event> findEventById(Long idEvent) {
        Optional<Event> event = super.findEventById(idEvent);
        if(event.isEmpty()){
            throw new EventNotFoundException("Evento não encontrado!");
        }
        updateEventStatus(event.get().getId());
        return event;
    }

    @Override
    public List<Event> findAllEventsByOrganizer(UUID uuidOrganizer) {
        List<Event> events = super.findAllEventsByOrganizer(uuidOrganizer);
        return applyStatusUpdateToEvents(events);
    }

    @Override
    public List<Event> findEventsByOrganizerUuidAndStatus(UUID uuidOrganizer, StatusEventEnum status) {
        List<Event> events = super.findEventsByOrganizerUuidAndStatus(uuidOrganizer, status);
        applyStatusUpdateToEvents(events);
        return events.stream()
                .filter(e -> e.getStatus() == status)
                .collect(Collectors.toList());
    }

    @Override
    public List<Event> findAllEvents() {
        List<Event> events = super.findAllEvents();
        return applyStatusUpdateToEvents(events);
    }

    @Override
    public List<Event> findAllEventsAvailableForSubscribe(UUID uuidParticipant) {
        List<Event> events = super.findAllEventsAvailableForSubscribe(uuidParticipant);
        return applyStatusUpdateToEvents(events);
    }

    @Override
    public List<Event> findAllOngoingEvents(UUID uuidParticipant) {
        List<Event> events = super.findAllOngoingEvents(uuidParticipant);
        return applyStatusUpdateToEvents(events);
    }

    @Override
    public List<Event> findAllEventsThatTheParticipantIsSubscribe(UUID uuidParticipant) {
        List<Event> events = super.findAllEventsThatTheParticipantIsSubscribe(uuidParticipant);
        return applyStatusUpdateToEvents(events);
    }
}
