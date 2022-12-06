package com.uno.error;

import com.uno.constance.ErrorCode;
import com.uno.dto.ApiErrorResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class BaseErrorController implements ErrorController {

    @RequestMapping(value = "/error", produces = MediaType.TEXT_HTML_VALUE )
    public ModelAndView errorHtml(HttpServletResponse res){

        HttpStatus status = HttpStatus.valueOf(res.getStatus());
        ErrorCode errorCode = status.is4xxClientError()?ErrorCode.BAD_REQUEST:ErrorCode.INTERNAL_ERROR;
        return new ModelAndView("error",
                                Map.of(  "statusCode",status
                                        ,"errorCode" ,errorCode
                                        ,"message", errorCode.getMessage(status.getReasonPhrase())
                                    )
                                ,status
                                );
    }

    @RequestMapping("/error")
    public ResponseEntity<ApiErrorResponse> error(HttpServletResponse response) {
        HttpStatus status = HttpStatus.valueOf(response.getStatus());
        ErrorCode errorCode = status.is4xxClientError() ? ErrorCode.BAD_REQUEST : ErrorCode.INTERNAL_ERROR;

        return ResponseEntity
                .status(status)
                .body(ApiErrorResponse.of(false,errorCode));
    }
}
