package com.example.project_events.facade;

import com.example.project_events.dto.RegisterEventDTO;
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

    public void createEvent(UUID uuid, RegisterEventDTO registerEventDTO){
        eventService.registerEvent(uuid, registerEventDTO);
    }

    public void updateEvent(UUID uuid, Long idEvent, RegisterEventDTO registerEventDTO){
        eventService.updateEvent(uuid, idEvent, registerEventDTO);
    }

    public void deleteEvent(UUID uuid, Long idEvent){
        eventService.deleteEvent(uuid, idEvent);
    }

    public Event findEventById(UUID uuid, Long idEvent){
        return eventService.findEventById(uuid, idEvent);
    }

    public List<Event> findAllEvents(){
        return eventService.findAllEvents();
    }

    public List<Event> findAllEventsByOrganizer(UUID uuid){
        return eventService.findAllEventsByOrganizer(uuid);
    }
}
