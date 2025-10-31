package com.example.project_events.decorator.concrete_decorators;

import com.example.project_events.decorator.component.IEventSearchComponent;
import com.example.project_events.decorator.abstract_decorator.EventSearchDecorator;
import com.example.project_events.enums.StatusEventEnum;
import com.example.project_events.model.Event;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class SortNumberOfSubscribersDecorator extends EventSearchDecorator {
    public SortNumberOfSubscribersDecorator(IEventSearchComponent decoratedSearch) {
        super(decoratedSearch);
    }

    private List<Event> applySort(List<Event> events){
        events.sort(Comparator.comparing((Event e) -> e.getAmountOfSubscribers()).reversed());
        return events;
    }

    @Override
    public List<Event> findAllEventsByOrganizer(UUID uuidOrganizer) {
        List<Event> events = super.findAllEventsByOrganizer(uuidOrganizer);
        return applySort(events);
    }

    @Override
    public List<Event> findEventsByOrganizerUuidAndStatus(UUID uuidOrganizer, StatusEventEnum status) {
        List<Event> events = super.findEventsByOrganizerUuidAndStatus(uuidOrganizer, status);
        return applySort(events);
    }

    @Override
    public List<Event> findAllEvents() {
        List<Event> events = super.findAllEvents();
        return applySort(events);
    }

    @Override
    public List<Event> findAllEventsAvailableForSubscribe(UUID uuidParticipant) {
        List<Event> events = super.findAllEventsAvailableForSubscribe(uuidParticipant);
        return applySort(events);
    }

    @Override
    public List<Event> findAllOngoingEvents(UUID uuidParticipant) {
        List<Event> events = super.findAllOngoingEvents(uuidParticipant);
        return applySort(events);
    }

    @Override
    public List<Event> findAllEventsThatTheParticipantIsSubscribe(UUID uuidParticipant) {
        List<Event> events = super.findAllEventsThatTheParticipantIsSubscribe(uuidParticipant);
        return applySort(events);
    }
}
