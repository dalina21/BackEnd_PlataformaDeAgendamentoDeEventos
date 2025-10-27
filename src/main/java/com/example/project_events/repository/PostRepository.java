package com.example.project_events.repository;

import com.example.project_events.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
    Post save(Post post);
    Optional<Post> findById(long id);
    void delete(Post post);
    List<Post> findAllByOrganizer_UuidAndEvent_Id(UUID uuidOrganizer, long idEvent);
    List<Post> findAllByParticipants_UuidAndEvent_Id(UUID uuidParticipant, long idEvent);
}
