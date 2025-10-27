package com.example.project_events.facade;

import com.example.project_events.dto.RegisterEventDTO;
import com.example.project_events.dto.ResponseEventDTO;
import com.example.project_events.enums.StatusEventEnum;
import com.example.project_events.model.Event;
import com.example.project_events.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EventFacade {

    private final EventService eventService;

    public void createEvent(UUID uuidOrganizer, RegisterEventDTO registerEventDTO){
        eventService.registerEvent(uuidOrganizer, registerEventDTO);
    }

    public void updateEvent(UUID uuidOrganizer, Long idEvent, RegisterEventDTO registerEventDTO){
        eventService.updateEvent(uuidOrganizer, idEvent, registerEventDTO);
    }

    public void deleteEvent(UUID uuidOrganizer, Long idEvent){
        eventService.deleteEvent(uuidOrganizer, idEvent);
    }

    public ResponseEventDTO findEventById(UUID uuidOrganizer, Long idEvent){
        return eventService.findEventById(uuidOrganizer, idEvent);
    }

    public List<ResponseEventDTO> findAllEvents(){
        return eventService.findAllEvents();
    }

    public List<ResponseEventDTO> findAllEventsByOrganizer(UUID uuidOrganizer){
        return eventService.findAllEventsByOrganizer(uuidOrganizer);
    }

    public List<ResponseEventDTO> findEventsByOrganizerUuidAndStatus(UUID uuidOrganizer, StatusEventEnum status){
        return eventService.findEventsByOrganizerUuidAndStatus(uuidOrganizer, status);
    }

    public void subscribeForAnEvent(UUID uuidParticipant, Long idEvent){
        eventService.subscribeForAnEvent(uuidParticipant, idEvent);
    }

    public void cancelSubscribeForAnEvent(UUID uuidParticipant, Long idEvent){
        eventService.cancelSubscribeForAnEvent(uuidParticipant, idEvent);
    }

    public List<ResponseEventDTO> findAllEventsAvailableForSubscribe(UUID uuidParticipant){
        return eventService.findAllEventsAvailableForSubscribe(uuidParticipant);
    }

    public List<ResponseEventDTO> findAllOngoingEvents(UUID uuidParticipant){
        return eventService.findAllOngoingEvents(uuidParticipant);
    }

    public List<ResponseEventDTO> findAllEventsThatTheParticipantIsSubscribe(UUID uuidParticipant){
        return eventService.findAllEventsThatTheParticipantIsSubscribe(uuidParticipant);
    }
}
