package com.example.project_events.repository;

import com.example.project_events.model.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrganizerRepository extends JpaRepository<Organizer, UUID> {
    Organizer save(Organizer organizer);
    Optional<Organizer> findByUuid(UUID uuid);
    Optional<Organizer> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByEmailAndUuidNot(String email, UUID uuid);
    boolean existsByUuid(UUID uuid);
}
