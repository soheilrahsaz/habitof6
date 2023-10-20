package com.moses33.habitof6.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class BaseResponse<T> extends ResponseEntity<BaseResponseBody<T>> {
    public BaseResponse(HttpStatusCode status) {
        this((T) null, status);
    }

    public BaseResponse(T result) {
        this(result, HttpStatus.OK);
    }

    public BaseResponse(T result, HttpStatusCode status) {
        super(new BaseResponseBody<>(result, null), status);
    }

    public BaseResponse(String error, HttpStatusCode status) {
        super(new BaseResponseBody<>(null, error), status);
    }

}

@Data
@NoArgsConstructor
@AllArgsConstructor
class BaseResponseBody<T>{
    private T result;
    private String error;
}
