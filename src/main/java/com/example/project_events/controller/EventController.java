package com.example.project_events.controller;

import com.example.project_events.dto.RegisterEventDTO;
import com.example.project_events.dto.ResponseEventDTO;
import com.example.project_events.dto.ResponseUserDTO;
import com.example.project_events.enums.StatusEventEnum;
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

    @PostMapping("/{uuidOrganizer}/register")
    public ResponseEntity<?> registerEvent(@PathVariable UUID uuidOrganizer, @RequestBody @Valid RegisterEventDTO registerEventDTO){
        eventFacade.createEvent(uuidOrganizer, registerEventDTO);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Evento criado com sucesso!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{uuidOrganizer}/{idEvent}/update")
    public ResponseEntity<?> updateEvent(@PathVariable UUID uuidOrganizer, @PathVariable Long idEvent, @RequestBody @Valid RegisterEventDTO registerEventDTO){
        eventFacade.updateEvent(uuidOrganizer, idEvent, registerEventDTO);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Evento atualizado com sucesso!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{uuidOrganizer}/{idEvent}/delete")
    public ResponseEntity<?> deleteEvent(@PathVariable UUID uuidOrganizer, @PathVariable Long idEvent){
        eventFacade.deleteEvent(uuidOrganizer, idEvent);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Evento deletado com sucesso!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Retorna todos os registros de eventos")
    @GetMapping("/find-all")
    public ResponseEntity<?> findAllEvents(){
        Map<String, List<ResponseEventDTO>> response = new HashMap<>();
        response.put("events", eventFacade.findAllEvents());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Retorna um evento especifico")
    @GetMapping("/{uuidOrganizer}/{idEvent}/find-by-id")
    public ResponseEntity<?> findEventById(@PathVariable UUID uuidOrganizer, @PathVariable Long idEvent){
        Map<String, ResponseEventDTO> response = new HashMap<>();
        response.put("event", eventFacade.findEventById(uuidOrganizer, idEvent));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Retorna todos os registros de eventos criados por um organizador")
    @GetMapping("/{uuidOrganizer}/find-by-organizer")
    public ResponseEntity<?> findAllEventsByOrganizer(@PathVariable UUID uuidOrganizer){
        Map<String, List<ResponseEventDTO>> response = new HashMap<>();
        response.put("events", eventFacade.findAllEventsByOrganizer(uuidOrganizer));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Retorna todos os registros de eventos de um organizador filtrados pelo status")
    @GetMapping("/{uuidOrganizer}/find-by-status")
    public ResponseEntity<?> findEventsStatusByOrganizer(@PathVariable UUID uuidOrganizer, @RequestParam StatusEventEnum status){
        Map<String, List<ResponseEventDTO>> response = new HashMap<>();
        response.put("events", eventFacade.findEventsByOrganizerUuidAndStatus(uuidOrganizer, status));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Realiza inscrição de um participante em um evento")
    @PostMapping("/{uuidParticipant}/{idEvent}/subscribe")
    public ResponseEntity<?> subscribeForAnEvent(@PathVariable UUID uuidParticipant, @PathVariable Long idEvent){
        eventFacade.subscribeForAnEvent(uuidParticipant, idEvent);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Inscrição realizada com sucesso!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Cancela inscrição de um participante em um evento")
    @DeleteMapping("/{uuidParticipant}/{idEvent}/cancel-inscription")
    public ResponseEntity<?> cancelSubscribeForAnEvent(@PathVariable UUID uuidParticipant, @PathVariable Long idEvent){
        eventFacade.cancelSubscribeForAnEvent(uuidParticipant, idEvent);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Inscrição cancelada com sucesso!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Retorna todos os eventos que estão dispiniveis para a inscrição")
    @GetMapping("/{uuidParticipant}/available-events")
    public ResponseEntity<?> findAllEventsAvailableForSubscribe(@PathVariable UUID uuidParticipant){
        List<ResponseEventDTO> events = eventFacade.findAllEventsAvailableForSubscribe(uuidParticipant);
        Map<String, List<ResponseEventDTO>> response = new HashMap<>();
        response.put("available events", events);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Retorna todos os eventos que estão em andamento")
    @GetMapping("/{uuidParticipant}/ongoing-events")
    public ResponseEntity<?> findAllOngoingEvents(@PathVariable UUID uuidParticipant){
        List<ResponseEventDTO> events = eventFacade.findAllOngoingEvents(uuidParticipant);
        Map<String, List<ResponseEventDTO>> response = new HashMap<>();
        response.put("ongoing events", events);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Retorna todos os eventos em que um participante está inscrito")
    @GetMapping("/{uuidParticipant}/events-subscribed")
    public ResponseEntity<?> findAllEventsThatTheParticipantIsSubscribe(@PathVariable UUID uuidParticipant){
        List<ResponseEventDTO> events = eventFacade.findAllEventsThatTheParticipantIsSubscribe(uuidParticipant);
        Map<String, List<ResponseEventDTO>> response = new HashMap<>();
        response.put("events subscribed", events);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Retorna todos os participantes inscritos em um evento")
    @GetMapping("/{uuidOrganizer}/{idEvent}/participants-subscribes")
    public ResponseEntity<?> listParticipantsOfAnEvent(@PathVariable UUID uuidOrganizer, @PathVariable Long idEvent){
        List<ResponseUserDTO> participants = eventFacade.listParticipantsOfAnEvent(uuidOrganizer, idEvent);
        Map<String, List<ResponseUserDTO>> response = new HashMap<>();
        response.put("participants subscribed", participants);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
