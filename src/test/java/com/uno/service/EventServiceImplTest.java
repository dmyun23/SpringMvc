package com.uno.service;

import com.uno.constance.EventStatus;
import com.uno.domain.Event;
import com.uno.dto.EventDto;
import com.uno.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @InjectMocks
    private EventServiceImpl sut;
    @Mock
    private EventRepository eventRepository;

    @DisplayName("검색 조건 없이 이벤트 검색하면, 전체 결과를 출력해서 보여준다.")
    @Test
    void givenNothing_whenSearchingEvents_thenReturnEntireEventList(){

        //given
        when(eventRepository.findEvents(null,null,null,null,null))
                .thenReturn(List.of(
                        createEventDto(1L,"오전 운동", true),
                        createEventDto(1L,"오후 운동", false)
                ));

        //When
        List<EventDto> list = sut.getEvents(null,null,null,null,null);

        //Then
        assertThat(list).hasSize(2);
        verify(eventRepository).findEvents(null,null,null,null,null);
    }

    @DisplayName("검색 조건과 함께 이벤트 검색하면, 전체 결과를 출력해서 보여준다.")
    @Test
    void givenSearchingParam_whenSearchingEvents_thenReturnEntireEventList(){

        //given
        Long placeId = 1L;
        String eventName = "오전 운동";
        EventStatus eventStatus = EventStatus.OPENED;
        LocalDateTime eventStartDateTime = LocalDateTime.of(2021,1,1,0,0);
        LocalDateTime eventEndDateTime = LocalDateTime.of(2021,1,2,0,0);

        when(eventRepository.findEvents(placeId, eventName, eventStatus, eventStartDateTime, eventEndDateTime))
                .thenReturn(List.of(
                   createEventDto(1L,"오전 운동", eventStatus, eventStartDateTime, eventEndDateTime)
                ));

        //When
        List<EventDto> list = sut.getEvents(placeId,eventName,eventStatus,eventStartDateTime,eventEndDateTime);

        //Then
        assertThat(list).hasSize(1)
                .allSatisfy(event->{
                    assertThat(event)
                            .hasFieldOrPropertyWithValue("placeId",placeId)
                            .hasFieldOrPropertyWithValue("eventName",eventName)
                            .hasFieldOrPropertyWithValue("eventStatus", eventStatus);
                    assertThat(event.getEventStartDatetime()).isAfterOrEqualTo(eventStartDateTime);
                    assertThat(event.getEventStartDatetime()).isBeforeOrEqualTo(eventStartDateTime);
                });
        verify(eventRepository).findEvents(placeId, eventName, eventStatus, eventStartDateTime, eventEndDateTime);
    }

    @DisplayName("이벤트 ID로 존재하는 이벤트를 조회하면, 해당 이벤트 정보를 출력한다.")
    @Test
    void givenEventId_whenSearchingExistingEvent_thenReturnEvent(){
        //given
        long eventId = 1L;
        EventDto eventDto = createEventDto(1L,"오전 운동",true);
        when(eventRepository.findEvent(eventId))
                .thenReturn(Optional.of(eventDto));

        //When
        Optional<EventDto> result = sut.getEvent(eventId);

        //Then
        assertThat(result).hasValue(eventDto);
        verify(eventRepository).findEvent(eventId);
    }

    @DisplayName("이벤트 ID로  이벤트를 조회하면, 빈 정보를 출력하여 보여준다.")
    @Test
    void givenEventId_whenSearchingNonExistentEvent_thenReturnEmptyOptional(){
        //given
        long eventId = 2L;
        when(eventRepository.findEvent(eventId))
                .thenReturn(Optional.empty());

        //When
        Optional<EventDto> result = sut.getEvent(eventId);

        //Then
        assertThat(result).isEmpty();
        verify(eventRepository).findEvent(eventId);
    }

    @DisplayName("이벤트 정보를 주면, 이벤트를 생성하고 결과를 true로 보여준다.")
    @Test
    void givenEvent_whenCreating_thenCreateEventAndReturnTrue(){
        //given
        EventDto eventDto = createEventDto(1L, "오후 운동", false);
        when(eventRepository.insertEvent(eventDto))
                .thenReturn(true);

        //When
        boolean result = sut.createEvent(eventDto);

        //Then
        assertThat(result).isTrue();
        verify(eventRepository).insertEvent(eventDto);
    }

    @DisplayName("이벤트 정보를 주지 않으면,생성 중단하고 결과를 false로 보여준다.")
    @Test
    void givenNothing_whenCreating_thenAbortCreatingAndReturnFalse(){
        //given
        when(eventRepository.insertEvent(null))
                .thenReturn(false);

        //When
        boolean result = sut.createEvent(null);

        //Then
        assertThat(result).isFalse();
        verify(eventRepository).insertEvent(null);
    }

    @DisplayName("이벤트 ID와 정보를 주면, 이벤트 정보를 변경하고 결과를 true로 보여준다.")
    @Test
    void givenEventIdAndItsInfo_whenModifying_thenModifiedEventAndReturnTrue(){
        //given
        Long eventId = 1L;
        EventDto eventDto = createEventDto(1L, "오후 운동", false);
        when(eventRepository.updateEvent(eventId,eventDto))
                .thenReturn(true);

        //When
        boolean result = sut.modifyEvent(eventId, eventDto);

        //Then
        assertThat(result).isTrue();
        verify(eventRepository).updateEvent(eventId,eventDto);
    }

    @DisplayName("이벤트 ID를 주지않고 변경할 정보만 주면 , 이벤트 정보 변경 중단하고 결과를 false를 보여준다.")
    @Test
    void givenNoEventID_whenModifying_thenAbortModifyingReturnfalse(){
        //given
        EventDto eventDto = createEventDto(1L, "오후 운동",false);
        when(eventRepository.updateEvent(null,eventDto))
                .thenReturn(false);

        //When
        boolean result = sut.modifyEvent(null,eventDto);

        //Then
        assertThat(result).isFalse();
        verify(eventRepository).updateEvent(null,eventDto);
    }

    @DisplayName("이벤트 ID만 주고 변경할 정보를 주지 않으면, 이벤트 정보 변경 중단하고 결과를 false를 보여준다.")
    @Test
    void givenNoEvenetInfo_whenModifying_thenAbortModifyingReturnfalse(){
        //given
        Long eventId = 1L;
        when(eventRepository.updateEvent(eventId,null))
                .thenReturn(false);

        //When
        boolean result = sut.modifyEvent(eventId,null);

        //Then
        assertThat(result).isFalse();
        verify(eventRepository, times(1)).updateEvent(eventId,null);
    }

    @DisplayName("이벤트 ID를 주면, 이벤트 정보를 삭제하고 결과를 true로 보여준다.")
    @Test
    void givenEventId_whenDeleting_thenDeleteEventAndReturnTrue(){
        //given
        Long eventId = 1L;
        when(eventRepository.deleteEvent(eventId))
                .thenReturn(true);

        //When
        boolean result = sut.removeEvent(eventId);

        //Then
        assertThat(result).isTrue();
        verify(eventRepository).deleteEvent(eventId);
    }

    @DisplayName("이벤트 ID를 주지 않으면, 삭제 중단하고 결과를 false를 리턴한다.")
    @Test
    void givenNothingEventId_whenDeleting_thenAbortDeletingAndReturnFalse(){
        //given
        when(eventRepository.deleteEvent(null))
                .thenReturn(false);

        //When
        boolean result = sut.removeEvent(null);

        //Then
        assertThat(result).isFalse();
        verify(eventRepository).deleteEvent(null);
    }


    private EventDto createEventDto( long placeId, String eventName, boolean isMorning){
        String hourStart = isMorning ? "09" : "13";
        String hourEnd = isMorning ? "12" : "16";

        return createEventDto(
                placeId,
                eventName,
                EventStatus.OPENED,
                LocalDateTime.of(2021,1,1,Integer.valueOf(hourStart),0,0),
                LocalDateTime.of(2021,1,1,Integer.valueOf(hourEnd),0,0)
        );
    }

    private EventDto createEventDto(
            long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDateTime,
            LocalDateTime eventEndDateTime
    ){
        return EventDto.of(
                placeId,
                eventName,
                eventStatus,
                eventStartDateTime,
                eventEndDateTime,
                0,
                24,
                "마스크 꼭 착용하세요.",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}