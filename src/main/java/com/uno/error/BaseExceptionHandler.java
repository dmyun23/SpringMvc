package com.uno.error;

import com.uno.constance.ErrorCode;
import com.uno.dto.ApiErrorResponse;
import com.uno.exception.GeneralException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler
    public ModelAndView  general(GeneralException e){
        ErrorCode errorCode = e.getErrorCode();
        HttpStatus status = errorCode.isClientSideError()?
                                HttpStatus.BAD_REQUEST:
                                HttpStatus.INTERNAL_SERVER_ERROR;

        return new ModelAndView("error",
                    Map.of("statusCode",status.value(),
                            "errorCode", errorCode,
                            "message",errorCode.getMessage(e)
                        ),
                    status
                );
    }

    @ExceptionHandler
    public ModelAndView  exception(Exception e){
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return new ModelAndView("error",
                Map.of("statusCode",status.value(),
                        "errorCode", errorCode,
                        "message",errorCode.getMessage(e)
                ),
                status
        );
    }
}
