package com.example.project_events.model;

import com.example.project_events.enums.StatusEventEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "event")
@Data
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private StatusEventEnum status;

    private String name;
    private String description;
    private LocalDate eventDate;
    private int limitParticipants;
    private int amountOfSubscribers;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private Organizer organizer;

    @OneToMany(mappedBy = "event")
    private List<Post> posts;

    @ManyToMany(mappedBy = "events")
    private List<Participant> participants;
}
