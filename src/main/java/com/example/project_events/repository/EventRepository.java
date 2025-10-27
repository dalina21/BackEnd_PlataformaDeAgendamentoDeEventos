package com.example.project_events.repository;

import com.example.project_events.enums.StatusEventEnum;
import com.example.project_events.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{
    Event save(Event event);
    Optional<Event> findById(long id);
    void delete(Event event);
    List<Event> findAll();
    List<Event> findAllByOrganizerUuid(UUID uuid);
    List<Event> findAllByOrganizerUuidAndStatus(UUID uuid, StatusEventEnum status);
    List<Event> findAllByStatus(StatusEventEnum status);
    List<Event> findAllByParticipants_Uuid(UUID uuid);
}
