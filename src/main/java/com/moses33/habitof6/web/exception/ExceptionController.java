package com.moses33.habitof6.web.exception;

import com.moses33.habitof6.web.response.BaseResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UserFriendlyException.class)
    public BaseResponse<Object> handleUserFriendlyException(UserFriendlyException userFriendlyException)
    {
        return new BaseResponse<>(userFriendlyException.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AuthenticationException.class)
    public BaseResponse<Object> handleUserFriendlyException(AuthenticationException authenticationException)
    {
        return new BaseResponse<>(authenticationException.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public BaseResponse<Object> handleOptimisticLockingFailureException(OptimisticLockingFailureException optimisticLockingFailureException)
    {
        return new BaseResponse<>(optimisticLockingFailureException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<Object> handleNotValidException(MethodArgumentNotValidException exception) {
        Map<String, List<String>> exceptionResults = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(FieldError::getField, Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())));

        return new BaseResponse<>("InvalidInput", exceptionResults, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public BaseResponse<Object> handleNotValidException(ConstraintViolationException exception) {
        Map<String, List<String>> exceptionResults = exception.getConstraintViolations()
                .stream()
                .collect(Collectors.groupingBy(c -> c.getPropertyPath().toString(), Collectors.mapping(ConstraintViolation::getMessage, Collectors.toList())));

        return new BaseResponse<>("InvalidDBInput", exceptionResults, HttpStatus.BAD_REQUEST);
    }

}
