package com.example.project_events.controller;

import com.example.project_events.dto.CreatePostDTO;
import com.example.project_events.dto.ResponsePostDTO;
import com.example.project_events.facade.PostFacade;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostFacade postFacade;

    @PostMapping("/{uuidOrganizer}/{idEvent}/create")
    public ResponseEntity<?> createPost(@PathVariable UUID uuidOrganizer, @PathVariable Long idEvent, @RequestBody CreatePostDTO createPostDTO){
        postFacade.createPost(uuidOrganizer, idEvent, createPostDTO);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Postagem criada com sucesso!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{uuidOrganizer}/{idPost}/delete")
    public ResponseEntity<?> deletePost(@PathVariable UUID uuidOrganizer, @PathVariable Long idPost){
        postFacade.deletePost(uuidOrganizer, idPost);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Postagem deletada com sucesso!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Retorna os posts de um organizador em um evento")
    @GetMapping("/organizer/{uuidOrganizer}/{idEvent}")
    public ResponseEntity<?> findAllByOrganizerUuuidAndEvent(@PathVariable UUID uuidOrganizer, @PathVariable Long idEvent){
        List<ResponsePostDTO> posts = postFacade.findAllByOrganizerUuuidAndEvent(uuidOrganizer, idEvent);
        Map<String, List<ResponsePostDTO>> response = new HashMap<>();
        response.put("posts", posts);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Retorna os posts que um participante pode visualizar em um evento que ele está inscrito")
    @GetMapping("/participant/{uuidParticipant}/{idEvent}")
    public ResponseEntity<?> findAllByParticipantUuidAndEvent(@PathVariable UUID uuidParticipant, @PathVariable Long idEvent){
        List<ResponsePostDTO> posts = postFacade.findAllByParticipantUuidAndEvent(uuidParticipant, idEvent);
        Map<String, List<ResponsePostDTO>> response = new HashMap<>();
        response.put("posts", posts);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
