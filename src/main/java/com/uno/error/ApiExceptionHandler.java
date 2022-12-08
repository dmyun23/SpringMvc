package com.uno.error;

import com.uno.constance.ErrorCode;
import com.uno.dto.ApiErrorResponse;
import com.uno.exception.GeneralException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice(annotations=RestController.class)
public class ApiExceptionHandler extends ResponseEntityExceptionHandler  {

    @ExceptionHandler
    public ResponseEntity<Object> general(ConstraintViolationException e, WebRequest request){
        ErrorCode errorCode = ErrorCode.VALIDATION_ERROR;
        HttpStatus status = HttpStatus.BAD_REQUEST;

        return super.handleExceptionInternal(
                e,
                ApiErrorResponse.of(false,errorCode, errorCode.getMessage(e)),
                HttpHeaders.EMPTY,
                status,
                request
        );
    }

    @ExceptionHandler
    public ResponseEntity<Object> general(GeneralException e, WebRequest request){
//    public ResponseEntity<ApiErrorResponse> general(GeneralException e,WebRequest request){
        ErrorCode errorCode = e.getErrorCode();
        HttpStatus status = errorCode.isClientSideError()?
                            HttpStatus.BAD_REQUEST :
                            HttpStatus.INTERNAL_SERVER_ERROR;

        return super.handleExceptionInternal(
                e,
                ApiErrorResponse.of(false,errorCode, errorCode.getMessage(e)),
                HttpHeaders.EMPTY,
                status,
                request
        );
//
//        return ResponseEntity
//                .status(status)
//                .body(ApiErrorResponse.of(
//                                false,errorCode, errorCode.getMessage(e))
//                );
    }

    @ExceptionHandler
    public ResponseEntity<Object> exception(Exception e,WebRequest request){
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
        HttpStatus status =  HttpStatus.INTERNAL_SERVER_ERROR;

        return super.handleExceptionInternal(
                e,
                ApiErrorResponse.of(false,errorCode, errorCode.getMessage(e)),
                HttpHeaders.EMPTY,
                status,
                request
        );

//        return ResponseEntity.
//                status(status).
//                body(ApiErrorResponse.of(
//                        false, errorCode, errorCode.getMessage(e)
//                ));
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorCode errorCode = status.is4xxClientError() ?
                            ErrorCode.SPRING_BAD_REQUEST :
                            ErrorCode.SPRING_INTERNAL_ERROR;


        return super.handleExceptionInternal(
                    ex,
                    ApiErrorResponse.of(false,errorCode, errorCode.getMessage(ex)),
                    headers,
                    status,
                    request
                );
    }
}
