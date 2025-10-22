package com.example.project_events.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "organizer")
@Data
public class Organizer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    private String name;
    private String password;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "organizer")
    private List<Event> events;

    @OneToMany(mappedBy = "organizer")
    private List<Post> posts;
}
