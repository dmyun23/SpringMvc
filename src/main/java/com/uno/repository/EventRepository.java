package com.uno.repository;

import com.uno.constance.EventStatus;
import com.uno.dto.EventDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// TODO: 인스턴스 생성 편의를 위해 임시로 default 사용. repository layer 구현이 완성되면 삭제할 것.
public interface EventRepository {
    default List<EventDto> findEvents(
              Long placeId,
              String eventName,
              EventStatus eventStatus,
              LocalDateTime eventStartDateTime,
              LocalDateTime eventEndDateTime
    ){ return null; }
    default Optional<EventDto> findEvent(Long eventId) { return Optional.empty(); }
    default boolean insertEvent(EventDto eventDto) { return false; }
    default boolean updateEvent(Long eventId, EventDto eventDto) { return false; }
    default boolean deleteEvent(Long eventId) { return false; }
}
