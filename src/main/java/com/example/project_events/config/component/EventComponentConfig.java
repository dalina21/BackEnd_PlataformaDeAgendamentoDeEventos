package com.example.project_events.config.component;

import com.example.project_events.decorator.component.IEventSearchComponent;
import com.example.project_events.decorator.concrete_component.EventSearchBaseComponent;
import com.example.project_events.decorator.concrete_decorators.*;
import com.example.project_events.repository.EventRepository;
import com.example.project_events.repository.OrganizerRepository;
import com.example.project_events.repository.ParticipantRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventComponentConfig {
    @Bean
    public IEventSearchComponent eventSearchComponent(
            EventRepository eventRepository,
            OrganizerRepository organizerRepository,
            ParticipantRepository participantRepository
    ) {
        IEventSearchComponent component = new EventSearchBaseComponent(eventRepository, organizerRepository, participantRepository);
        component = new StatusUpdateDecorator(component, eventRepository);
        component = new SortNumberOfSubscribersDecorator(component);

        return component;
    }
}
