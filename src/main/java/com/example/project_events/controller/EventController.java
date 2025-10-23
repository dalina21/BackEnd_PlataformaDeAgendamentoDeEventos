package com.example.project_events.controller;

import com.example.project_events.dto.RegisterEventDTO;
import com.example.project_events.facade.EventFacade;
import com.example.project_events.model.Event;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventFacade eventFacade;

    @PostMapping("/{uuid}/register")
    public ResponseEntity<?> registerEvent(@PathVariable UUID uuid, @RequestBody @Valid RegisterEventDTO registerEventDTO){
        eventFacade.createEvent(uuid, registerEventDTO);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Evento criado com sucesso!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{uuid}/{idEvent}/update")
    public ResponseEntity<?> updateEvent(@PathVariable UUID uuid, @PathVariable Long idEvent, @RequestBody @Valid RegisterEventDTO registerEventDTO){
        eventFacade.updateEvent(uuid, idEvent, registerEventDTO);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Evento atualizado com sucesso!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}/{idEvent}/delete")
    public ResponseEntity<?> deleteEvent(@PathVariable UUID uuid, @PathVariable Long idEvent){
        eventFacade.deleteEvent(uuid, idEvent);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Evento deletado com sucesso!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Retorna todos os registros de eventos")
    @GetMapping("/find-all")
    public ResponseEntity<?> findAllEvents(){
        Map<String, List<Event>> response = new HashMap<>();
        response.put("events", eventFacade.findAllEvents());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Retorna um evento especifico")
    @GetMapping("/{uuid}/{idEvent}/find-by-id")
    public ResponseEntity<?> findEventById(@PathVariable UUID uuid, @PathVariable Long idEvent){
        Map<String, Event> response = new HashMap<>();
        response.put("event", eventFacade.findEventById(uuid, idEvent));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Retorna todos os registros de eventos criados por um determinado organizador")
    @GetMapping("/{uuid}/find-by-organizer")
    public ResponseEntity<?> findAllEventsByOrganizer(@PathVariable UUID uuid, @PathVariable Long idEvent){
        Map<String, List<Event>> response = new HashMap<>();
        response.put("events", eventFacade.findAllEventsByOrganizer(uuid));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
