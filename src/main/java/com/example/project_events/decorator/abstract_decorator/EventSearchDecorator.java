package com.example.project_events.decorator.abstract_decorator;

import com.example.project_events.decorator.component.IEventSearchComponent;
import com.example.project_events.enums.StatusEventEnum;
import com.example.project_events.model.Event;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public abstract class EventSearchDecorator implements IEventSearchComponent {

    protected final IEventSearchComponent decoratedSearch;

    @Override
    public Optional<Event> findEventById(Long idEvent) {
        return decoratedSearch.findEventById(idEvent);
    }

    @Override
    public List<Event> findAllEventsByOrganizer(UUID uuidOrganizer) {
        return decoratedSearch.findAllEventsByOrganizer(uuidOrganizer);
    }

    @Override
    public List<Event> findEventsByOrganizerUuidAndStatus(UUID uuidOrganizer, StatusEventEnum status) {
        return decoratedSearch.findEventsByOrganizerUuidAndStatus(uuidOrganizer, status);
    }

    @Override
    public List<Event> findAllEvents() {
        return decoratedSearch.findAllEvents();
    }

    @Override
    public List<Event> findAllEventsAvailableForSubscribe(UUID uuidParticipant) {
        return decoratedSearch.findAllEventsAvailableForSubscribe(uuidParticipant);
    }

    @Override
    public List<Event> findAllOngoingEvents(UUID uuidParticipant) {
        return decoratedSearch.findAllOngoingEvents(uuidParticipant);
    }

    @Override
    public List<Event> findAllEventsThatTheParticipantIsSubscribe(UUID uuidParticipant) {
        return decoratedSearch.findAllEventsThatTheParticipantIsSubscribe(uuidParticipant);
    }
}
