package com.example.project_events.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post")
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String message;
    private LocalDate postingDate;

    @ManyToOne
    @JoinColumn(name = "organizer_uuid")
    private Organizer organizer;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToMany
    @JoinTable(
            name = "view_posts",
            joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "participant_uuid"))
    private List<Participant> participants = new ArrayList<>();
}
