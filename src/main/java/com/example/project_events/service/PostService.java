package com.example.project_events.service;

import com.example.project_events.dto.CreatePostDTO;
import com.example.project_events.dto.ResponsePostDTO;
import com.example.project_events.errors.EventNotFoundException;
import com.example.project_events.errors.PostNotFoundException;
import com.example.project_events.errors.UnauthorizedException;
import com.example.project_events.errors.UuidNotFoundException;
import com.example.project_events.model.Event;
import com.example.project_events.model.Organizer;
import com.example.project_events.model.Participant;
import com.example.project_events.model.Post;
import com.example.project_events.repository.EventRepository;
import com.example.project_events.repository.OrganizerRepository;
import com.example.project_events.repository.ParticipantRepository;
import com.example.project_events.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final OrganizerRepository organizerRepository;
    private final ParticipantRepository participantRepository;
    private final EventRepository eventRepository;

    private void incrementNotificationsOfOnePost(Long postId){
        Optional<Post> post = postRepository.findById(postId);
        if(post.isEmpty()){
            throw new PostNotFoundException("Post não encontrado!");
        }

        for (Participant p : post.get().getParticipants()){
            p.setCounterNotification(p.getCounterNotification() + 1);
        }

        participantRepository.saveAll(post.get().getParticipants());
    }

    public void createPost(UUID uuidOrganizer, Long idEvent, CreatePostDTO createPostDTO){
        Optional<Organizer> organizer = organizerRepository.findByUuid(uuidOrganizer);
        Optional<Event> event = eventRepository.findById(idEvent);

        if (organizer.isEmpty()){
            throw new UuidNotFoundException("Uuid do organizador não encontrado!");
        }
        if(event.isEmpty()){
            throw new EventNotFoundException("Evento não encontrado!");
        }
        if(!event.get().getOrganizer().getUuid().equals(uuidOrganizer)){
            throw new UnauthorizedException("Este organizador não tem permissão para criar postagem nesse evento!");
        }

        Post post = new Post();
        post.setMessage(createPostDTO.getMessage());
        post.setPostingDate(LocalDate.now());
        post.setOrganizer(organizer.get());
        post.setEvent(event.get());
        post.getParticipants().addAll(event.get().getParticipants());
        postRepository.save(post);

        incrementNotificationsOfOnePost(post.getId());
    }

    public void deletePost(UUID uuidOrganizer, Long idPost){
        Optional<Post> post = postRepository.findById(idPost);

        if (!organizerRepository.existsByUuid(uuidOrganizer)){
            throw new UuidNotFoundException("Uuid do organizador não encontrado!");
        }
        if(post.isEmpty()){
            throw new PostNotFoundException("Postagem não encontrada!");
        }
        if(!post.get().getOrganizer().getUuid().equals(uuidOrganizer)){
            throw new UnauthorizedException("Este organizador não tem permissão para deletar essa postagem");
        }

        post.get().getParticipants().forEach(p -> p.getPosts().remove(post.get()));
        postRepository.delete(post.get());
    }

    public List<ResponsePostDTO> findAllByOrganizerUuuidAndEvent(UUID uuidOrganizer, Long idEvent){
        List<Post> posts = postRepository.findAllByOrganizer_UuidAndEvent_Id(uuidOrganizer, idEvent);

        if (!organizerRepository.existsByUuid(uuidOrganizer)){
            throw new UuidNotFoundException("Uuid do organizador não encontrado!");
        }
        if(posts.isEmpty()){
            throw new PostNotFoundException("Nenhuma postagem nesse evento foi encontrada!");
        }
        if(!eventRepository.existsById(idEvent)){
            throw new EventNotFoundException("Evento não encontrado!");
        }
        if(!eventRepository.findById(idEvent).get().getOrganizer().getUuid().equals(uuidOrganizer)){
            throw new UnauthorizedException("Este organizador não tem permissão para vizualizar as postagens desse evento");
        }

        return posts.stream()
                .map(p -> new ResponsePostDTO(
                      p.getMessage(),
                      p.getPostingDate(),
                      p.getOrganizer().getName()
                )).toList();
    }

    public List<ResponsePostDTO> findAllByParticipantUuidAndEvent(UUID uuidParticipant, Long idEvent){
        List<Post> posts = postRepository.findAllByParticipants_UuidAndEvent_Id(uuidParticipant, idEvent);

        if (!participantRepository.existsById(uuidParticipant)){
            throw new UuidNotFoundException("Uuid do participante não encontrado!");
        }
        if(posts.isEmpty()){
            throw new PostNotFoundException("Nenhuma postagem nesse evento foi encontrada!");
        }
        if(!eventRepository.existsById(idEvent)){
            throw new EventNotFoundException("Evento não encontrado!");
        }

        return posts.stream()
                .map(p -> new ResponsePostDTO(
                        p.getMessage(),
                        p.getPostingDate(),
                        p.getOrganizer().getName()
                )).toList();
    }
}
