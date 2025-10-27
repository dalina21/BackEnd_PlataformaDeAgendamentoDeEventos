package com.example.project_events.facade;

import com.example.project_events.dto.CreatePostDTO;
import com.example.project_events.dto.ResponsePostDTO;
import com.example.project_events.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PostFacade {

    private final PostService postService;

    public void createPost(UUID uuidOrganizer, Long idEvent, CreatePostDTO createPostDTO){
        postService.createPost(uuidOrganizer, idEvent, createPostDTO);
    }

    public void deletePost(UUID uuidOrganizer, Long idPost){
        postService.deletePost(uuidOrganizer, idPost);
    }

    public List<ResponsePostDTO> findAllByOrganizerUuuidAndEvent(UUID uuidOrganizer, Long idEvent){
        return postService.findAllByOrganizerUuuidAndEvent(uuidOrganizer, idEvent);
    }

    public List<ResponsePostDTO> findAllByParticipantUuidAndEvent(UUID uuidParticipant, Long idEvent){
        return postService.findAllByParticipantUuidAndEvent(uuidParticipant, idEvent);
    }
}
