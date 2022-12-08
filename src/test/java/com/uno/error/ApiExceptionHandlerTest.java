package com.uno.error;

import com.uno.constance.ErrorCode;
import com.uno.dto.ApiErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.handler.DispatcherServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ApiExceptionHandlerTest {

    private ApiExceptionHandler sut;
    private WebRequest webRequest;

    @BeforeEach
    void setSut(){
        sut = new ApiExceptionHandler();
        webRequest = new DispatcherServletWebRequest(new MockHttpServletRequest());
    }

    @DisplayName("검증 오류 - 응답 데이터 정의")
    @Test
    void givenException_whenCallingValidation_thenReturnResponseEntity(){
        //given
        ConstraintViolationException e = new ConstraintViolationException(Set.of());

        //When
        ResponseEntity<Object> response = sut.validation(e, webRequest);
        response.getBody();
        response.getHeaders();
        response.getStatusCode();

        //Then
        assertThat(response).isNotNull()
                .hasFieldOrPropertyWithValue("body", ApiErrorResponse.of(false, ErrorCode.VALIDATION_ERROR, ErrorCode.VALIDATION_ERROR.getMessage(e)))
                .hasFieldOrPropertyWithValue("headers", HttpHeaders.EMPTY)
                .hasFieldOrPropertyWithValue("status", HttpStatus.BAD_REQUEST);
    }
}