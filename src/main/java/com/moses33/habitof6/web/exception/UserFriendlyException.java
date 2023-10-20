package com.moses33.habitof6.web.exception;

public class UserFriendlyException extends RuntimeException{
    public UserFriendlyException(String message) {
        super(message);
    }

    public UserFriendlyException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserFriendlyException(Throwable cause) {
        super(cause);
    }

    public UserFriendlyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
