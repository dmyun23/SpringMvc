package com.uno.dto;

import com.uno.constance.EventStatus;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@ToString @EqualsAndHashCode
public class EventRequest {

    @NotNull @Positive private final Long id;
    @NotBlank private final String eventName;
    @NotNull private final EventStatus eventStatus;
    @NotNull private final LocalDateTime eventStartDatetime;
    @NotNull private final LocalDateTime eventEndDatetime;
    @NotNull @PositiveOrZero private final Integer currentNumberOfPeople;
    @NotNull private final Integer capacity;
    private final String memo;

    public static EventRequest of(
            Long id,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime,
            Integer currentNumberOfPeople,
            Integer capacity,
            String memo
    ){
        return new EventRequest(id,eventName, eventStatus,eventStartDatetime,eventEndDatetime
                                ,currentNumberOfPeople,capacity,memo);
    }

    public EventDto toDto() {
        return EventDto.of(
                this.id,
                this.eventName,
                this.eventStatus,
                this.eventStartDatetime,
                this.eventEndDatetime,
                this.currentNumberOfPeople,
                this.capacity,
                this.memo,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}
