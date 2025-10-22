package com.example.project_events.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "participant")
@Data
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    private String name;
    private String password;

    @Column(unique = true)
    private String email;

    private int counterNotification;

    @ManyToMany
    @JoinTable(
            name = "participate_event",
            joinColumns = @JoinColumn(name = "participant_id"),inverseJoinColumns = @JoinColumn(name = "event_id"))
    private List<Event> events;

    @ManyToMany
    @JoinTable(
            name = "view_posts",
            joinColumns = @JoinColumn(name = "participant_id"), inverseJoinColumns = @JoinColumn(name = "post_id"))
    private List<Post> posts;
}
