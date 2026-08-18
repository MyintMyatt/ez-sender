package com.ezsender.EzSender.exception;

import com.ezsender.EzSender.constant.ErrorType;
import com.ezsender.EzSender.models.response.ApiResponse;
import com.ezsender.EzSender.models.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.ezsender.EzSender.constant.ErrorType.InternalServer;

@RestControllerAdvice
@Slf4j
public class GlobalRestExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ApiResponse<ErrorResponse> handle(RuntimeException ex){
        log.error("Application Exception", ex);
        return ApiResponse.error(
                new ErrorResponse(InternalServer, ex.getMessage())
        );
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ApiResponse<ErrorResponse> handle(ValidationException ex){
        log.error("Validation", ex);
        return ApiResponse.error(new ErrorResponse(ErrorType.Validation,
                ex.getMessages()));
    }

}
