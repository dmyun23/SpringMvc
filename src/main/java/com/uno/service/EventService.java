package com.uno.service;

import com.uno.constance.EventStatus;
import com.uno.dto.EventDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 *   이벤트 서비스
 *
 * @author  Ydm
 */
public interface EventService {
    /**
     *
     * @param placeId  장소ID
     * @param eventName  이벤트 이름
     * @param eventStatus  이벤트 상태
     * @param eventStartDatetime  시작시간
     * @param eventEndDatetime  종료시간
     * @return 다중 이벤트 조회
     */
    List<EventDto> getEvents(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime
    );

    /**
     *
     * @param eventId 이벤트 ID
     * @return 단건 이벤트 조회
     */
    Optional<EventDto> getEvent(Long eventId);

    /**
     *
     * @param eventDto 이벤트 생성 정보
     * @return 이벤트 만들기
     */
    boolean createEvent(EventDto eventDto);

    /**
     *
     * @param eventId 이벤트 ID
     * @param eventDto 이벤트 변경 정보
     * @return 이벤트 수정하기
     */
    boolean modifyEvent(Long eventId, EventDto eventDto);

    /**
     *
     * @param eventId 이벤트 ID 
     * @return 이벤트 삭제하기
     */
    boolean removeEvent(Long eventId);

}
