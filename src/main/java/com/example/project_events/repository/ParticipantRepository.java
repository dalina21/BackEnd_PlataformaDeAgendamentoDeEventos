package com.example.project_events.repository;

import com.example.project_events.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, UUID> {
    Optional<Participant> findByEmail(String email);
    Optional<Participant> findByUuid(UUID uuid);
}
