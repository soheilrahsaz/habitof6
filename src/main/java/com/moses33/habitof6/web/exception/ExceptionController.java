package com.moses33.habitof6.web.exception;

import com.moses33.habitof6.web.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UserFriendlyException.class)
    public BaseResponse<Object> handleUserFriendlyException(UserFriendlyException userFriendlyException)
    {
        return new BaseResponse<>(userFriendlyException.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
