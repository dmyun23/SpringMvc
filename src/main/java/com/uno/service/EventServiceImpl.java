package com.uno.service;

import com.uno.constance.EventStatus;
import com.uno.dto.EventDto;
import com.uno.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements  EventService{

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<EventDto> getEvents(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime
    ) {
        return eventRepository.findEvents(placeId,eventName,eventStatus,eventStartDatetime,eventEndDatetime);
    }

    @Override
    public Optional<EventDto> getEvent(Long eventId) {
        return eventRepository.findEvent(eventId);
    }

    @Override
    public boolean createEvent(EventDto eventDto) {
        return eventRepository.insertEvent(eventDto);
    }

    @Override
    public boolean modifyEvent(Long eventId, EventDto eventDto) {
        return eventRepository.updateEvent(eventId,eventDto);
    }

    @Override
    public boolean removeEvent(Long eventId) {
        return eventRepository.deleteEvent(eventId);
    }
}
