package com.uno.controller.api;

import com.uno.constance.ErrorCode;
import com.uno.constance.EventStatus;
import com.uno.dto.*;
import com.uno.exception.GeneralException;
import com.uno.service.EventServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ApiEventController {

    private final EventServiceImpl eventService;

    @GetMapping("/events")
    public ApiDataResponse<List<EventResponse>> getEvents(
            @Positive  Long placeId,
            @Size(min = 2) String eventName,
            EventStatus eventStatus,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventStartDatetime,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventEndDatetime
    ) {

         List<EventResponse> responses = eventService
                .getEvents(placeId,eventName,eventStatus,eventStartDatetime,eventEndDatetime)
                .stream().map(EventResponse::from).collect(Collectors.toList());
         return ApiDataResponse.of(responses);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/events")
    public ApiDataResponse<String> createEvent(@Valid @RequestBody EventRequest eventRequest){
        boolean result = eventService.createEvent(eventRequest.toDto());

        return ApiDataResponse.of(Boolean.toString(result));
    }

    @GetMapping("/events/{eventId}")
    public ApiDataResponse<EventResponse> getEvent(@PathVariable Integer eventId) {
        return ApiDataResponse.empty();
    }

    @PutMapping("/events/{eventId}")
    public ApiDataResponse<Void> modifyEvent(
            @PathVariable Long eventId,
            @RequestBody EventRequest eventRequest
    ){
        return ApiDataResponse.empty();
    }

    @DeleteMapping("/events/{eventId}")
    public ApiDataResponse<Void> removeEvent(
            @PathVariable Long eventId
    ){
        return ApiDataResponse.empty();
    }

//    @ExceptionHandler
//    public ResponseEntity<ApiErrorResponse> general(GeneralException e) {
//        ErrorCode errorCode = e.getErrorCode();
//        HttpStatus status = errorCode.isClientSideError()?
//                HttpStatus.BAD_REQUEST :
//                HttpStatus.INTERNAL_SERVER_ERROR;
//
//        return ResponseEntity.status(status)
//                .body(ApiDataResponse.of(false,errorCode.getCode(), errorCode.getMessage(e)));
//    }
}
