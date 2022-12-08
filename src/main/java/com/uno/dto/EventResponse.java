package com.uno.dto;

import com.uno.constance.EventStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class EventResponse {

    private final Long placeId;
    private final String eventName;
    private final EventStatus eventStatus;
    private final LocalDateTime eventStartDatetime;
    private final LocalDateTime eventEndDatetime;
    private final Integer currentNumberOfPeople;
    private final Integer capacity;
    private final String memo;

    public static EventResponse of(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime,
            Integer currentNumberOfPeople,
            Integer capacity,
            String memo
    ){
        return new EventResponse(
                    placeId,
                    eventName,
                    eventStatus,
                    eventStartDatetime,
                    eventEndDatetime,
                    currentNumberOfPeople,
                    capacity,
                    memo
                );
    }

    public static EventResponse from(EventDto eventDto){
        if(eventDto == null) { return null; }
        return EventResponse.of(
                eventDto.getPlaceId(),
                eventDto.getEventName(),
                eventDto.getEventStatus(),
                eventDto.getEventStartDatetime(),
                eventDto.getEventEndDatetime(),
                eventDto.getCurrentNumberOfPeople(),
                eventDto.getCapacity(),
                eventDto.getMemo()
        );
    }



}
