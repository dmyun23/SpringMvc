package com.uno.constance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.*;

class ErrorCodeTest {

    @ParameterizedTest(name = "{index} {0} ===> {1}")
    @MethodSource
    @DisplayName("예외를 받으면, 예외 메시지가 포함된 메시지 출력.")
    void gievenExceptionWithMessage_whenGettingMessage_thenReturnMessage(ErrorCode sut,String expected){
        //given
        Exception e = new Exception("This is test message");

        //When
        String result = sut.getMessage(e);

        //Then
        assertThat(result).isEqualTo(expected);
    }

    static Stream<Arguments> gievenExceptionWithMessage_whenGettingMessage_thenReturnMessage(){
        return Stream.of(
          arguments(ErrorCode.OK, "OK - This is test message"),
          arguments(ErrorCode.BAD_REQUEST, "Bad request - This is test message"),
          arguments(ErrorCode.SPRING_BAD_REQUEST, "Spring-detected bad request - This is test message"),
          arguments(ErrorCode.VALIDATION_ERROR, "Validation error - This is test message"),
          arguments(ErrorCode.INTERNAL_ERROR, "Internal error - This is test message"),
          arguments(ErrorCode.SPRING_INTERNAL_ERROR, "Spring-detected internal error - This is test message")
        );
    }

    @ParameterizedTest(name = "[{index}] \"{0}\" ===> \"{1}\"")
    @MethodSource
    @DisplayName("예외를 받으면, 해당 에러메시지 그대로 출력.")
    void gievenMessage_whenGettingMessage_thenReturnMessage(String input,String expected){
        //given
        Exception e = new Exception("This is test message");

        //When
        String result = ErrorCode.INTERNAL_ERROR.getMessage(input);

        //Then
        assertThat(result).isEqualTo(expected);
    }

    static Stream<Arguments> gievenMessage_whenGettingMessage_thenReturnMessage(){
        return Stream.of(
                arguments(null, ErrorCode.INTERNAL_ERROR.getMessage()),
                arguments("", ErrorCode.INTERNAL_ERROR.getMessage()),
                arguments("   ", ErrorCode.INTERNAL_ERROR.getMessage()),
                arguments("This is test message", "This is test message")
        );
    }

    @DisplayName("toString() 호출 포맷")
    @Test
    void gievenErroCode_whenToString_thenReturnsSimplifiedToString(){
        //given

        //When
        String result = ErrorCode.INTERNAL_ERROR.toString();

        //Then
        assertThat(result).isEqualTo("INTERNAL_ERROR (20000)");
    }

}