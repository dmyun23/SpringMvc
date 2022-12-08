package com.uno.config;

import com.uno.repository.EventRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RepositoryConfig {

    @Bean
    public EventRepository eventRepository(){
        return new EventRepository() {};
    }
}
