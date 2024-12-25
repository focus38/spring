package ru.focus.spring.auth.exception;

import lombok.Data;

@Data
public class AppException extends RuntimeException {

    private final int status;
    private final String url;

    public AppException(final int status, final String url, final String message) {
        super(message);
        this.status = status;
        this.url = url;
    }
}
