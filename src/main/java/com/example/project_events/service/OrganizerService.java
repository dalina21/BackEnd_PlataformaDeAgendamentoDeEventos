package com.example.project_events.service;

import com.example.project_events.repository.OrganizerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizerService {

    private final OrganizerRepository organizerRepository;


}
