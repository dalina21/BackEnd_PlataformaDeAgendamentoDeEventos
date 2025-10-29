package com.example.project_events.decorator.component;

import com.example.project_events.dto.ResponseEventDTO;
import com.example.project_events.enums.StatusEventEnum;
import com.example.project_events.model.Event;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IEventSearchComponent {
    Optional<Event> findEventById(Long idEvent);
    List<Event> findAllEventsByOrganizer(UUID uuidOrganizer);
    List<Event> findEventsByOrganizerUuidAndStatus(UUID uuidOrganizer, StatusEventEnum status);
    List<Event> findAllEvents();
    List<Event> findAllEventsAvailableForSubscribe(UUID uuidParticipant);
    List<Event> findAllOngoingEvents(UUID uuidParticipant);
    List<Event> findAllEventsThatTheParticipantIsSubscribe(UUID uuidParticipant);
}
