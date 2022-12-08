package com.uno.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uno.constance.ErrorCode;
import com.uno.constance.EventStatus;
import com.uno.dto.EventDto;
import com.uno.dto.EventRequest;
import com.uno.service.EventServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ApiEventController.class)
class ApiEventControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper mapper;

    @MockBean
    private EventServiceImpl eventService;

    public ApiEventControllerTest(
            @Autowired MockMvc mvc,
            @Autowired ObjectMapper mapper
    ) {
        this.mvc = mvc;
        this.mapper = mapper;
    }

    @DisplayName("[API][GET] 이벤트 리스트 조회")
    @Test
    void givenParams_whenReqeustingEvents_thenReturnsListOfEventsInStandardResponse() throws Exception {
        //given
        when(eventService.getEvents(any(),any(),any(),any(),any()))
                .thenReturn(List.of(createEventDto()));

        //When
        mvc.perform(get("/api/events")
                        .queryParam("placeId","1")
                        .queryParam("eventName","운동")
                        .queryParam("eventStatus",EventStatus.OPENED.name())
                        .queryParam("eventStartDatetime","2021-01-01T00:00:00")
                        .queryParam("eventEndDatetime","2021-01-02T00:00:00")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].placeId").value(1L))
                .andExpect(jsonPath("$.data[0].eventName").value("오후 운동"))
                .andExpect(jsonPath("$.data[0].eventStatus").value(EventStatus.OPENED.name()))
                .andExpect(jsonPath("$.data[0].eventStartDatetime").value(LocalDateTime
                        .of(2021, 1, 1, 13, 0, 0)
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .andExpect(jsonPath("$.data[0].eventEndDatetime").value(LocalDateTime
                        .of(2021, 1, 1, 16, 0, 0)
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .andExpect(jsonPath("$.data[0].currentNumberOfPeople").value(0))
                .andExpect(jsonPath("$.data[0].capacity").value(24))
                .andExpect(jsonPath("$.data[0].memo").value("마스크 꼭 착용하세요"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()))
                ;

        //Then
        verify(eventService).getEvents(any(),any(),any(),any(),any());
    }

    @DisplayName("[API][GET] 이벤트 리스트 조회")
    @Test
    void givenWrongParams_whenReqeustingEvents_thenReturnsFailedStandardResponse() throws Exception {
        //given

        //When
        mvc.perform(get("/api/events")
                        .queryParam("placeId","0")
                        .queryParam("eventName","오")
                        .queryParam("eventStatus",EventStatus.OPENED.name())
                        .queryParam("eventStartDatetime","2021-01-01T00:00:00")
                        .queryParam("eventEndDatetime","2021-01-02T00:00:00")
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.VALIDATION_ERROR.getCode()))
                .andExpect(jsonPath("$.message").value(containsString(ErrorCode.VALIDATION_ERROR.getMessage())))
        ;

        //Then
//        verify(eventService).getEvents(any(),any(),any(),any(),any());
    }

    @DisplayName("[API][POST] 이벤트 생성")
    @Test
    void givenEvent_whenCreatingEvent_thenReturnSuccessStandardResponse() throws Exception {

        //given
        EventRequest eventRequest = EventRequest.of(
                1L,
                "오후 운동",
                EventStatus.OPENED,
                LocalDateTime.of(2021, 1, 1, 13, 0, 0),
                LocalDateTime.of(2021, 1, 1, 16, 0, 0),
                0,
                24,
                "마스크 꼭 착용하세요"
        );

        when(eventService.createEvent(any()))
                .thenReturn(true);

        //When
        mvc.perform(post("/api/events")
                        .content(mapper.writeValueAsString(eventRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").value(Boolean.TRUE.toString()))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()))
                ;
        //Then
        verify(eventService).createEvent(any());
    }

    @DisplayName("[API][POST] 이벤트 생성 - 잘못된 데이터 입력 ")
    @Test
    void givenWrongEvent_whenCreatingEvent_thenReturnFailedStandardResponse() throws Exception {

        //given
        EventRequest eventRequest = EventRequest.of(
                -1L,
                "  ",
                null,
                null,
                null,
                -1,
                0,
                "마스크 꼭 착용하세요"
        );
        //When
        mvc.perform(post("/api/events")
                        .content(mapper.writeValueAsString(eventRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.SPRING_BAD_REQUEST.getCode()))
                .andExpect(jsonPath("$.message").value(containsString(ErrorCode.SPRING_BAD_REQUEST.getMessage())))
        ;
        //Then
    }

    private EventDto createEventDto(){
        return EventDto.of(
                1L,
                "오후 운동",
                EventStatus.OPENED,
                LocalDateTime.of(2021, 1, 1, 13, 0, 0),
                LocalDateTime.of(2021, 1, 1, 16, 0, 0),
                0,
                24,
                "마스크 꼭 착용하세요",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}